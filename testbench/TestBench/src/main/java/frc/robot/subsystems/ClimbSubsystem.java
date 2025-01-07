// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LiftConstants;
import frc.robot.util.UqUtil;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
  private final TalonFX m_motor = new TalonFX(ClimbConstants.kMotorCanId);
  // if you need a new motor just make it follow m_lift_a
  
  public ClimbSubsystem() {
    m_motor.setNeutralMode(NeutralModeValue.Brake);
  }

  /**
   * @param power -1.0 to 1.0
   * @return
   */
  public Command movePct(double power) {
    return run(
      () -> {
        double output_power = UqUtil.clamp(power, LiftConstants.kPctLimit);
        m_motor.set(output_power);
      }
    );
  }
}
