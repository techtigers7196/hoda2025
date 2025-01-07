// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LiftConstants;
import frc.robot.util.UqUtil;
import frc.robot.Constants.ControlSystem;

public class LiftSubsystem extends SubsystemBase {
  private final WPI_VictorSPX m_lift_a = new WPI_VictorSPX(LiftConstants.kMotorCanId);
  // if you need a new motor just make it follow m_lift_a
  private final DutyCycleEncoder m_enc = new DutyCycleEncoder(LiftConstants.kEncoderPwmPort);
  private double enc_pos;

  private final ProfiledPIDController m_ppid = new ProfiledPIDController(LiftConstants.Kp, LiftConstants.Ki, LiftConstants.Kd, 
    new TrapezoidProfile.Constraints(LiftConstants.kProfileMaxVel, LiftConstants.kProfileMaxAcc),
    ControlSystem.kT);

  private final DigitalInput m_hes_l_dn = new DigitalInput(LiftConstants.kHallDownDioPort);
  
  public LiftSubsystem() {
    m_lift_a.setNeutralMode(NeutralMode.Brake);
    m_lift_a.setInverted(LiftConstants.kInverted);
  }

  public Command goToPosition(double height) {
    return run(
      () -> {
        m_ppid.setGoal(height);
        double output_power = m_ppid.calculate(enc_pos);

        output_power = UqUtil.clamp(output_power, LiftConstants.kPctLimit);
        m_lift_a.set(ControlMode.PercentOutput,
          output_power
        );
      }
    );
  }

  /**
   * @param power -1.0 to 1.0
   * @return
   */
  public void movePct(double power, boolean includeFeedforward) {
    double output_power = power;
        
    if (!m_hes_l_dn.get() && output_power < 0.0) {
      // If bottom limit reached and power is driving lift down
      m_lift_a.set(ControlMode.PercentOutput, 0.0);
      
    } else {
      // we gucci
      output_power = power + (includeFeedforward ? LiftConstants.kFf : 0.0);
      // i knowingly clamp the power before adding the feedforward so we can do bringup sensibly
      m_lift_a.set(ControlMode.PercentOutput, output_power);
    }
  }

  /**
   * @return distance in rotations, probably
   */
  public double getDistance() {
    return m_enc.get();
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
    // This method will be called once per scheduler run
    enc_pos = m_enc.get();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
