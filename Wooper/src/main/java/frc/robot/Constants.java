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
    public static final int kSupportControllerPort = 1;
  }

  public static class DriveConstants {
    public static Double forwardPower = 0.7;
    public static Double turnPower = 0.7;
  }

  public static class ArmConstants {
      // Define Arm position constants
      public static Double positionIntakeCoral      = 1.02;
      public static Double positionClimbEnd         = 0.0;
      public static Double positionScoreCoral      = 0.94;
      public static Double positionRemoveAlgaeLow   = 0.91;
      public static Double positionClimbStart       = 0.0;
      public static Double positionRemoveAlgaeHigh  = 0.73;
      public static Double positionHoldAlgae        = 0.97;

      public static Double armPower = 0.6;
      public static Double armPowerSlow =0.15;
      // Define Arm position limits
      public static Double armFrontLimit            = 1.02;
      public static Double armRearLimit             = .72;
      public static double armRearLimitMax          = .9;

      // Define Arm velocity limit
      public static Double armVelocityLimit         = 0.6;

      // Define Arm PID constants
      public static Double armkP                    = 5.0;
      public static Double armkI                    = 0.0;
      public static Double armkD                    = 0.0;
      public static double armff                    = 0.03; //0.03
  }
}
