// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.function.FloatSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.PrintWriter;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class AlgaePole extends SubsystemBase {
  SparkMax poleMotor;
  SparkMaxConfig poleMotorConfig;


  /** Creates a new AlgaePole. */
  public AlgaePole() {
    poleMotor = new SparkMax(7, MotorType.kBrushed);

    poleMotorConfig = new SparkMaxConfig();

    poleMotor.configure(poleMotorConfig.
      inverted(true).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

  }

  public Command moveAlgaePole(Double velocity) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          poleMotor.set(velocity);
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
