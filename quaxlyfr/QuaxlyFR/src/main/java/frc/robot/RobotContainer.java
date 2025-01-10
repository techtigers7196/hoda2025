// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final ArmSubsystem m_arm = new ArmSubsystem();
  private final CoralSubsystem m_coral = new CoralSubsystem();
  private final AlgaeSubsystem m_algae = new AlgaeSubsystem();
  private final LiftSubsystem m_lift = new LiftSubsystem();
  private final ClimbSubsystem m_climb = new ClimbSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final Joystick m_driverController =
    new Joystick(OperatorConstants.kDriverControllerPort);

  private final CommandXboxController m_operatorController =
    new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Default commands
    m_drive.setDefaultCommand(
      Commands.run(
        () -> {
          m_drive.arcadeDrive(
            m_driverController.getX(), -m_driverController.getY()*0.6);
        },
        m_drive
      )
    );

    m_lift.setDefaultCommand(
      Commands.run(
        () -> {
          m_lift.movePct(-m_operatorController.getLeftY(), true);
        },
        m_lift
      )
    );
    
    m_arm.setDefaultCommand(
      Commands.run(
        () -> {
          m_arm.movePct(-m_operatorController.getRightY() * 0.5); 
        },
        m_arm
      )
    );
    
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
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    // m_driverController.leftStick().toggleOnTrue(m_drive.toggleForwardDirection());
    // TODO reverse intake / outtake depending on drive direction
    
    m_operatorController.leftBumper().whileTrue(m_coral.moveCoral(1.0));
    m_operatorController.leftTrigger().whileTrue(m_coral.moveCoral(-1.0));
    m_operatorController.rightBumper().whileTrue(m_algae.moveAlgae(0.6));
    m_operatorController.rightTrigger().whileTrue(m_algae.moveAlgae(-0.5));

    m_operatorController.povUp().whileTrue(m_climb.movePct(1.0));
    m_operatorController.povDown().whileTrue(m_climb.movePct(-1.0));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;  // FIXME
    // return Autos.exampleAuto(m_exampleSubsystem);
  }
}
