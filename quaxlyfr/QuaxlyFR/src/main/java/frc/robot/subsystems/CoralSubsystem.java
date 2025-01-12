// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.reduxrobotics.canand.CanandSettings;
import com.reduxrobotics.sensors.canandcolor.Canandcolor;
import com.reduxrobotics.sensors.canandcolor.CanandcolorSettings;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class CoralSubsystem extends SubsystemBase {

  private final WPI_VictorSPX m_coral = new WPI_VictorSPX(ArmConstants.kCoralCanId);

  private final Canandcolor m_clr1 = new Canandcolor(14);
  private final Canandcolor m_clr2 = new Canandcolor(15);
  
  public CoralSubsystem() {
    m_coral.setNeutralMode(NeutralMode.Brake);

    CanandcolorSettings clrSettings = new CanandcolorSettings();
    clrSettings.setLampLEDBrightness(0.0);
    m_clr1.setSettings(clrSettings);
    m_clr2.setSettings(clrSettings);
  }

  /**
   * @param power -1.0 to 1.0
   * @return
   */
  public void movePct(double power) {
    // double output_power = UqUtil.clamp(power, ArmConstants.kPctLimit);
    double output_power = power;
    m_coral.set(ControlMode.PercentOutput, output_power);
  }

  /**
   * @param power (+) front-to-back; (-) back-to-front
   * @return
   */
  public Command moveCoral(double power) {
    return startEnd(
      () -> {
        m_coral.set(ControlMode.PercentOutput, power);
      },

      () -> {
        m_coral.set(ControlMode.PercentOutput, 0.0);
      }
    );
  }

  /**
   * @return distance in rotations, probably
   */
  public double getDistance() {
    // return m_enc.getPosition().getValueAsDouble();
    return 0;
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
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
