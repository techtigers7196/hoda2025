// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;
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
  // private final AlgaePole m_algaepole = new AlgaePole();
  private final Intake m_intake = new Intake();
  private final Climber m_climber = new Climber();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  // Define Arm Position Constants
  Double positionIntakeCoral      = 0.4079;
  Double positionIntakeAlgae      = 0.338;
  Double positionClimbStart       = 0.233;
  Double positionClimbEnd         = 0.368;
  Double positionRemoveAlgaeLow   = 0.3083;
  Double positionRemoveAlgaeHigh  = 0.05855;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
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
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());


    // *** Drive bindings ***
    // Default behaviour (follow Y-axes of joysticks to implement tank drive)
    m_drive.setDefaultCommand(m_drive.driveTank(m_driverController::getLeftY, m_driverController::getRightY));    
    

    // *** Arm bindings ***
    // Default behaviour (keep position)
    // m_arm.setDefaultCommand(m_arm.moveArmToPosition(positionIntakeCoral));

    // Move to intake coral with Y
    m_driverController.y()
      .onTrue(m_arm.moveArmToPosition(positionIntakeCoral));

    // Move to intake algae with X
    m_driverController.x()
      .onTrue(m_arm.moveArmToPosition(positionIntakeAlgae));

    // Move to remove low-reef algae and dump L1 coral with B
    m_driverController.b()
      .onTrue(m_arm.moveArmToPosition(positionRemoveAlgaeLow));

    // Move to remove high-reef algae with A
    m_driverController.a()
      .onTrue(m_arm.moveArmToPosition(positionRemoveAlgaeHigh));

    // Move to start climb with D-Pad Down
    m_driverController.povDown()
      .onTrue(m_arm.moveArmToPosition(positionClimbStart));

    // Move to finish climb with D-Pad up
    m_driverController.povUp()
      .onTrue(m_arm.moveArmToPosition(positionClimbEnd));


    // // *** Algae Pole bindings ***
    // // Default behaviour (do nothing)
    // m_algaepole.setDefaultCommand(m_algaepole.moveAlgaePole(0.0));

    // // Raise algae pole with D-pad up 
    // m_driverController.povUp()
    //   .and(m_driverController.povDown().negate())
    //   .onTrue(m_algaepole.moveAlgaePole(15.0));

    // // Lower algae pole with D-pad down 
    // m_driverController.povDown()
    // .and(m_driverController.povUp().negate())
    // .onTrue(m_algaepole.moveAlgaePole(-15.0));

    // *** Intake bindings ***
     // Default behaviour (do nothing)
     m_intake.setDefaultCommand(m_intake.moveIntake(0.0));

     // Run intake with A button
     m_driverController.rightBumper()
      .and(m_driverController.rightTrigger().negate())
      .whileTrue(m_intake.moveIntake(0.75));
 
     // Run intake in reverse with B button
     m_driverController.rightTrigger()
      .and(m_driverController.rightBumper().negate()) 
      .whileTrue(m_intake.moveIntake(-0.75));

     // *** Climber bindings ***
     // Default behaviour (do nothing)
      m_climber.setDefaultCommand(m_climber.moveClimber(0.0));

      // Disengage climber with back button
      m_driverController.leftBumper()
        .and(m_driverController.leftTrigger().negate())
        .whileTrue(m_climber.moveClimber(0.3));

      // Engage climber with start buttom
      m_driverController.leftTrigger()
        .and(m_driverController.leftBumper().negate())
        .whileTrue(m_climber.moveClimber(-0.3));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drive);
  }
}
