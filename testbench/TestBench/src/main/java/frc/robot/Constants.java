// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  }

  public static class DriveConstants {
    // Drive motors
    public static final int kLeftMotor1Port = 3;
    public static final int kLeftMotor2Port = 4;
    public static final int kRightMotor1Port = 1;
    public static final int kRightMotor2Port = 2;

    // Encoders
    public static final int[] kLeftEncoderPorts = new int[] {0, 1};
    public static final boolean kLeftEncoderReversed = false;

    public static final int[] kRightEncoderPorts = new int[] {2, 3};
    public static final boolean kRightEncoderReversed = true;

    public static final double kEncoderDistancePerPulse = 0.0;  // FIXME
  }

  public static class LiftConstants {
    // For safety, needs to work, implemented in subsystems' commands
    public static final double kPctLimit = 0.2;

    public static final int kMotorCanId = 7;
    public static final int kEncoderPwmPort = 1;
    public static final int kHallDownDioPort = 0;

    public static final double Kp = 0.0;  //FIXME
    public static final double Kd = 0.0;
    public static final double Ki = 0.0;
    public static final double kProfileMaxVel = 0.0;
    public static final double kProfileMaxAcc = 0.0;

    public static final double kFf= 0.0;  // For gravity comp.
  }

  public static class LiftState {
    public static final double kHome = 0.0;
    public static final double kStow = 0.0;
    public static final double kAlgIntake = 0.0;  //FIXME
  }

  public static class ArmConstants {
    // For safety, needs to work, implemented in subsystems' commands
    public static final double kPctLimit = 0.2;

    public static final int kAlgaeCanId = 5;
    public static final int kCoralCanId = 6;
    public static final int kArmACanId = 7;
    
    public static final int kEncoderCanId = 13;
    
    public static final double Kp = 0.0;  //FIXME
    public static final double Kd = 0.0;
    public static final double Ki = 0.0;
    public static final double kProfileMaxVel = 0.0;
    public static final double kProfileMaxAcc = 0.0;
  }

  public static class ArmState {
    public static final double kHome = 0.0;
    public static final double kStow = 0.0;
    public static final double kAlgIntake = 0.0;  //FIXME
  }   

  public static class ClimbConstants {
    // For safety, needs to work, implemented in subsystems' commands
    public static final double kPctLimit = 1.0;

    public static final int kMotorCanId = 8;
  }
  
  public static class ControlSystem {
    public static final double kT = 0.01;  // Control system period (0.01 = 100Hz)
  }
}
