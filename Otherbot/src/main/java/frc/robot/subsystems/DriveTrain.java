// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.function.FloatSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.PrintWriter;
import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class DriveTrain extends SubsystemBase {
  SparkMax leftFront, leftRear, rightFront, rightRear;
  SparkMaxConfig leftFrontConfig, leftRearConfig, rightFrontConfig, rightRearConfig;


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    leftFront = new SparkMax(4, MotorType.kBrushless);
    leftRear = new SparkMax(3, MotorType.kBrushless);
    rightFront = new SparkMax(2, MotorType.kBrushless);
    rightRear = new SparkMax(1, MotorType.kBrushless);

    leftFrontConfig = new SparkMaxConfig();
    leftRearConfig = new SparkMaxConfig();
    rightFrontConfig = new SparkMaxConfig();
    rightRearConfig = new SparkMaxConfig();

    leftFront.configure(leftFrontConfig.
      inverted(false).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    leftRear.configure(leftRearConfig.
      inverted(false).
      idleMode(IdleMode.kBrake).
      follow(4), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightFront.configure(rightFrontConfig.
      inverted(true).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    rightRear.configure(rightRearConfig.
      inverted(true).
      idleMode(IdleMode.kBrake).
      follow(2), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

  }


 
  public Command driveTank(Double left, Double right) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          leftFront.set(left);
          rightFront.set(right);
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
