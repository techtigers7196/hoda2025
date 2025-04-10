// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class Intake2 extends SubsystemBase {
  SparkMax intakeMotor1, intakeMotor2;
  SparkMaxConfig intakeMotor1Config, intakeMotor2Config;


  /** Creates a new Intake. */
  public Intake2() {
    intakeMotor1 = new SparkMax(5, MotorType.kBrushed);
    intakeMotor2 = new SparkMax(6, MotorType.kBrushed);

    intakeMotor1Config = new SparkMaxConfig();
    intakeMotor2Config = new SparkMaxConfig();


    intakeMotor1.configure(intakeMotor1Config.
      inverted(false).
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);

    intakeMotor2.configure(intakeMotor2Config.
      idleMode(IdleMode.kBrake), 
      ResetMode.kNoResetSafeParameters, 
      PersistMode.kPersistParameters);


  }

  public Command moveIntake(Double velocity) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          intakeMotor1.set(velocity);
          intakeMotor2.set(velocity);
        });
  }

  public Command shootAlgae(Double velocity) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          intakeMotor1.set(velocity);
          intakeMotor2.set(velocity);
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
