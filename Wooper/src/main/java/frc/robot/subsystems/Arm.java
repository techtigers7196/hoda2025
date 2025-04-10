// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants.ArmConstants;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class Arm extends SubsystemBase {
  SparkMax armMotor1, armMotor2;
  SparkMaxConfig armMotor1Config, armMotor2Config;
  DutyCycleEncoder encoder;
  PIDController armP;
  Double armFrontLimit, armRearLimit, armVelocityLimit;
 
  DigitalInput limitSwitch;
  

  /** Creates a new Arm. */
  public Arm() {
    armMotor1 = new SparkMax(7, MotorType.kBrushless);
    armMotor2 = new SparkMax(8, MotorType.kBrushless);

    armMotor1Config = new SparkMaxConfig();
    armMotor2Config = new SparkMaxConfig();

    armMotor1.configure(armMotor1Config.
      inverted(true).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    armMotor2.configure(armMotor2Config.
      follow(armMotor1, true).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    encoder = new DutyCycleEncoder(1);
    armP = new PIDController(ArmConstants.armkP, ArmConstants.armkI, ArmConstants.armkD);

    limitSwitch = new DigitalInput(0);
  }

  private boolean isArmDown() {
    return !limitSwitch.get();
  }

  public Command moveArmUp()
  {
    return startEnd(() -> 
      this.moveArm(-ArmConstants.armPower), () -> {
        this.moveArm(0);   
    }).until(() -> isAtRearLimit()).andThen(
      () -> this.moveArm(0)
    );
  }



  public Command moveArmUpSlow()
  {
    return startEnd(() -> 
      this.moveArm(-ArmConstants.armPowerSlow), () -> {
        this.moveArm(0);   
    }).until(() -> isAtRearLimit()).andThen(
      () -> this.moveArm(0)
    );
  }

  public boolean isAtRearLimit()
  {
    return encoder.get() <= ArmConstants.armRearLimit && encoder.get() >= ArmConstants.armRearLimitMax;
  }


  public Command moveArmDown()
  {
    return startEnd(() -> 
      this.moveArm(ArmConstants.armPower), () -> {
        this.moveArm(0);   
    });
  }
    public Command moveArmDownSlow()
  {
    return startEnd(() -> 
      this.moveArm(ArmConstants.armPowerSlow), () -> {
        this.moveArm(0);   
    });
  }
    
    // .until(() -> isArmDown()).andThen(
    //   () -> this.moveArm(0)
    // );
  

  public void moveArm(double power)
  {
    this.armMotor1.set(power);
  }

  private double getEncoderValue()
  {
    double encoderValue = encoder.get();

    if (encoderValue<=0.3) {
      return encoderValue+0.99; 
    }
    return encoderValue;
  }


  public Command moveArmToPosition(Double position) {
    return run(
        () -> {
          
          // Get the target position, clamped to (limited between) the lowest and highest arm positions
          Double target = MathUtil.clamp(position, ArmConstants.armRearLimit, ArmConstants.armFrontLimit);

          Double pidValue = armP.calculate(getEncoderValue(), target);

          Double pidWithFf = pidValue - ArmConstants.armff;

          // Calculate the PID result, and clamp to the arm's maximum velocity limit.
          Double result =  MathUtil.clamp(pidWithFf, -1 * ArmConstants.armVelocityLimit, ArmConstants.armVelocityLimit);

          armMotor1.set(result);

          SmartDashboard.putNumber("pidValue", pidValue);
          SmartDashboard.putNumber("pidWithFf", pidWithFf);
          SmartDashboard.putNumber("result", result);
          SmartDashboard.putNumber("target", target);
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Raw Encoder Value", encoder.get());
    SmartDashboard.putNumber("Adjusted Encoder Value", getEncoderValue());
    SmartDashboard.putBoolean("Is Arm Down", isArmDown());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
