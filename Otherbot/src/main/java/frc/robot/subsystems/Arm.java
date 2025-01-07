// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.function.FloatSupplier;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.PrintWriter;

import javax.naming.spi.DirStateFactory.Result;

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
  

  /** Creates a new Arm. */
  public Arm() {
    armMotor1 = new SparkMax(6, MotorType.kBrushed);
    armMotor2 = new SparkMax(7, MotorType.kBrushed);

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

    encoder = new DutyCycleEncoder(0);
    armP = new PIDController(15, 0, 0);

    // Set arm limit / position range
    armFrontLimit = 0.4079;
    armRearLimit = 0.05855;

    // Set velocity % limit
    armVelocityLimit = 0.50;

  }

  public Command moveArm(Double velocity) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          armMotor1.set(velocity);
        });
  }

  public Command moveArmToPosition(Double position) {
    return run(
        () -> {
          Double currentPos = encoder.get();

          Double target = position;

          if (target > armFrontLimit) { 
            target = armFrontLimit; 
          }

          if (target < armRearLimit) { 
            target = armRearLimit; 
          }

          Double result = armP.calculate(encoder.get(), target);
          
          if (result < -1 * armVelocityLimit) {
            result = -1 * armVelocityLimit;
          }

          if (result > armVelocityLimit) {
            result = armVelocityLimit;
          }

          
          System.out.printf("%f %f %f \n", target, currentPos, result);

          armMotor1.set(result);

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
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
