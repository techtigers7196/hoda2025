// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ControlSystem;
import frc.robot.util.UqUtil;

public class ArmSubsystem extends SubsystemBase {
  private final WPI_TalonSRX m_arm_a = new WPI_TalonSRX(ArmConstants.kArmACanId);
  // if you need a new motor just make it follow m_motor
  // private final CANcoder m_enc = new CANcoder(ArmConstants.kEncoderCanId);
  private double enc_pos;

  private final ProfiledPIDController m_ppid = new ProfiledPIDController(ArmConstants.Kp, ArmConstants.Ki, ArmConstants.Kd, 
    new TrapezoidProfile.Constraints(ArmConstants.kProfileMaxVel, ArmConstants.kProfileMaxAcc),
    ControlSystem.kT);

  private final WPI_VictorSPX m_algae = new WPI_VictorSPX(ArmConstants.kAlgaeCanId);
  private final WPI_VictorSPX m_coral = new WPI_VictorSPX(ArmConstants.kCoralCanId);
  
  public ArmSubsystem() {
    m_arm_a.setNeutralMode(NeutralMode.Brake);
    m_algae.setNeutralMode(NeutralMode.Brake);
    m_algae.setInverted(true);
    m_coral.setNeutralMode(NeutralMode.Brake);
  }

  /**
   * @param height in rotations
   * @return
   */
  public Command goToPosition(double height) {
    return run(
      () -> {
        m_ppid.setGoal(height);
        double output_power = m_ppid.calculate(enc_pos);

        output_power = UqUtil.clamp(output_power, ArmConstants.kPctLimit);
        m_arm_a.set(ControlMode.PercentOutput,
          output_power
        );
      }
    );
  }

  /**
   * @param power -1.0 to 1.0
   * @return
   */
  public void movePct(double power) {
    // double output_power = UqUtil.clamp(power, ArmConstants.kPctLimit);
    double output_power = power;
    m_arm_a.set(ControlMode.PercentOutput, output_power);
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
        m_algae.set(ControlMode.PercentOutput, 0.0);
      }
    );
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
