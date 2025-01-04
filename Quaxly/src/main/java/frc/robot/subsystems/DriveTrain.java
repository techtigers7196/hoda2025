// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.function.FloatSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.PrintWriter;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveTrain extends SubsystemBase {
  TalonSRX leftFront, leftRear, rightFront, rightRear;


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    leftFront = new TalonSRX(4);
    leftRear = new TalonSRX(3);
    rightFront = new TalonSRX(1);
    rightRear = new TalonSRX(2);


    leftFront.setInverted(true);
    leftRear.setInverted(true);

    leftRear.follow(leftFront);
    rightRear.follow(rightFront);
  }


 
  public Command driveTank(DoubleSupplier left, DoubleSupplier right) {
    // Inline construction of command goes here.
    return run(
        () -> {
          
          leftFront.set(TalonSRXControlMode.PercentOutput, left.getAsDouble());
          rightFront.set(TalonSRXControlMode.PercentOutput, right.getAsDouble());
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
