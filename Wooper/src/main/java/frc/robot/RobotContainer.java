// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_drive = new DriveTrain();
  private final Arm m_arm = new Arm();
  private final Intake m_intake = new Intake();
  //private final Climber m_climber = new Climber();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final CommandXboxController m_supportController = new CommandXboxController(OperatorConstants.kSupportControllerPort);

  private final SendableChooser<String> autoChooser = new SendableChooser<String>();

  private final String kOneCoral = "One Coral Auto";
  private final String kDriveCoral = "Drive Coral Auto";

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    CameraServer.startAutomaticCapture();

    autoChooser.setDefaultOption(kOneCoral, kOneCoral);
    autoChooser.addOption(kOneCoral, kOneCoral);
    autoChooser.addOption(kDriveCoral, kDriveCoral);
  
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    // *** Drive bindings ***
    // Default behaviour (follow Y-axes of joysticks to implement tank drive)
    m_drive.setDefaultCommand(m_drive.driveArcade(m_driverController::getRightX, m_driverController::getLeftY));    
    
    m_driverController.y().whileTrue(m_drive.driveArcade(() -> 0, () -> 0));
    //m_driverController.b().onTrue(m_drive.driveArcade(() -> (m_driverController.getRightX()), () ->(m_driverController.getLeftY())));
    // *** Arm bindings ***

    m_supportController.leftBumper().whileTrue(m_arm.moveArmUpSlow());
    m_supportController.leftTrigger().whileTrue(m_arm.moveArmDownSlow());

    // Move to intake coral with Y
    m_supportController.povDown()
      .onTrue(m_arm.moveArmToPosition(ArmConstants.positionIntakeCoral));

    // Move to intake algae with X
    m_supportController.povLeft()
      .onTrue(m_arm.moveArmToPosition(ArmConstants.positionScoreCoral));

    // Move to remove low-reef algae and dump L1 coral with B
    m_supportController.povRight()
      .onTrue(m_arm.moveArmToPosition(ArmConstants.positionRemoveAlgaeLow));

    // Move to remove high-reef algae with A
    m_supportController.povUp()
      .onTrue(m_arm.moveArmToPosition(ArmConstants.positionRemoveAlgaeHigh));

    m_supportController.b()
    .onTrue(m_arm.moveArmToPosition(ArmConstants.positionHoldAlgae));


    // Move to start climb with D-Pad Down
    //m_driverController.povDown()
    //  .onTrue(m_arm.moveArmToPosition(ArmConstants.positionClimbStart));

    // Move to finish climb with D-Pad up
    //m_driverController.povUp()
    //  .onTrue(m_arm.moveArmToPosition(ArmConstants.positionClimbEnd));

    
    // *** Intake bindings ***
    // Default behaviour (do nothing)
    m_intake.setDefaultCommand(m_intake.moveIntake(0.0));

    // Outtake
    m_supportController.y()
      .and(m_supportController.a().negate())
      .onTrue(m_intake.moveIntake(1.0));

    // Intake
    m_supportController.a()
      .and(m_supportController.y().negate()) 
      .onTrue(m_intake.moveIntake(-1.0));

    m_supportController.x()
      .onTrue(m_intake.moveIntake(0.0));


    // *** Climber bindings ***
    // Default behaviour (do nothing)
    //m_climber.setDefaultCommand(m_climber.moveClimber(0.0));

    // Disengage climber with back button
    //m_driverController.leftBumper()
    //  .and(m_driverController.leftTrigger().negate())
    //  .whileTrue(m_climber.moveClimber(0.5));

    // Engage climber with start buttom
    //m_driverController.leftTrigger()
    //  .and(m_driverController.leftBumper().negate())
    //  .whileTrue(m_climber.moveClimber(-0.5));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // return Autos.autoSideLeft(m_drive, m_arm, m_intake);
    //return Autos.autoFoward(m_drive);
    if (autoChooser.getSelected() == kOneCoral)
    {
      return Autos.autoCoral1(m_drive, m_arm, m_intake);
    }
    if (autoChooser.getSelected()== kDriveCoral) {
      return Autos.autoDrive1(m_drive, m_arm, m_intake);
    }
    return Autos.autoCoral1(m_drive, m_arm, m_intake);
  }
}

