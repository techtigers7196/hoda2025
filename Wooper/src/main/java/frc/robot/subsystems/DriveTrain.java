// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
 
public class DriveTrain extends SubsystemBase {
  SparkMax leftFront, leftRear, rightFront, rightRear;
  SparkMaxConfig leftFrontConfig, leftRearConfig, rightFrontConfig, rightRearConfig;


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    leftFront = new SparkMax(2, MotorType.kBrushless);
    leftRear = new SparkMax(1, MotorType.kBrushless);
    rightFront = new SparkMax(3, MotorType.kBrushless);
    rightRear = new SparkMax(4, MotorType.kBrushless);

    leftFrontConfig = new SparkMaxConfig();
    leftRearConfig = new SparkMaxConfig();
    rightFrontConfig = new SparkMaxConfig();
    rightRearConfig = new SparkMaxConfig();

    leftFront.configure(leftFrontConfig.
      inverted(true).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    leftRear.configure(leftRearConfig.
      idleMode(IdleMode.kBrake).
      follow(2),
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightFront.configure(rightFrontConfig.
      inverted(false).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightRear.configure(rightRearConfig.
      idleMode(IdleMode.kBrake).
      follow(3),
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

  }


 
  public Command driveTank(DoubleSupplier left, DoubleSupplier right) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          leftFront.set(0.5*left.getAsDouble());
          rightFront.set(0.5*right.getAsDouble());
        });
  }

  public Command driveArcade(DoubleSupplier turn, DoubleSupplier forward)
  {
    return run(() -> {
      leftFront.set(DriveConstants.forwardPower*forward.getAsDouble() - DriveConstants.turnPower*turn.getAsDouble());
      rightFront.set(DriveConstants.forwardPower*forward.getAsDouble() + DriveConstants.turnPower*turn.getAsDouble());
    });
  }

  public Command moveStraight(Double velocity) {
    return run(
      () -> {
        leftFront.set(velocity);
        rightFront.set(velocity);
      });
  }

  public Command turn(Double velocity) {
    return run(
      () -> {
        leftFront.set(velocity);
        rightFront.set(-1 * velocity);
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
