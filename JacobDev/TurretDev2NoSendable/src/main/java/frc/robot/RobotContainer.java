/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Turret;
import frc.robot.commands.HomeTurret;
import frc.robot.commands.LockOnTurret;
import frc.robot.commands.OscillateTurret;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */

  private XboxController controller = new XboxController(2);

  private Command oscillateTurret = new OscillateTurret();
  private Command homeTurret = new HomeTurret();
  private Command lockOnTurret = new LockOnTurret();

  private Turret turret = Turret.getInstance();

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {  
    new JoystickButton(controller, Button.kA.value)
      .whenPressed(homeTurret);
    new JoystickButton(controller, Button.kX.value)
      .whenPressed(oscillateTurret);
    new JoystickButton(controller, Button.kB.value)
      .whenPressed(lockOnTurret);
      /*
    new JoystickButton(controller, Button.kBumperLeft.value)
      .whenHeld(new RunCommand(turret::setWantedAngle(45) turret));
    new JoystickButton(controller, Button.kBumperRight.value)
      .whenHeld(new RunCommand(turret::setWantedAngle(-45), turret));
      */
  }


    /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   *
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
  */
}
