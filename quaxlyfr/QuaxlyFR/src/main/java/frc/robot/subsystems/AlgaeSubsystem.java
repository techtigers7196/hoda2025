// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class AlgaeSubsystem extends SubsystemBase {

  private final WPI_VictorSPX m_algae = new WPI_VictorSPX(ArmConstants.kAlgaeCanId);
  
  public AlgaeSubsystem() {
    m_algae.setNeutralMode(NeutralMode.Brake);
    m_algae.setInverted(true);
  }

  /**
   * @param power -1.0 to 1.0
   * @return
   */
  public void movePct(double power) {
    // double output_power = UqUtil.clamp(power, ArmConstants.kPctLimit);
    double output_power = power;
    m_algae.set(ControlMode.PercentOutput, output_power);
  }

  /**
   * @param power + in; - out
   * @return
   */
  public Command moveAlgae(double power) {
    return startEnd(
      () -> {
        m_algae.set(ControlMode.PercentOutput, power);
      },

      () -> {
        m_algae.set(ControlMode.PercentOutput, 0.1);
      }
    );
  }

  @Override
  public void periodic() {
    // enc_pos = m_enc.getPosition().getValueAsDouble();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
