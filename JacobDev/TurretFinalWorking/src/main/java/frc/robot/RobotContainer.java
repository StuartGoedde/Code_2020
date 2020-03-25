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
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.OscillateTurret;
import frc.robot.commands.LockOnTurret;
import frc.robot.commands.TurretToZero;
import frc.robot.commands.ShootTurret;
import frc.robot.commands.StopShootTurret;
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
  private Command lockOnTurret = new LockOnTurret(() -> controller.getRawAxis(0), () -> controller.getYButton());
  private Command shootTurret = new ShootTurret();
  private Command stopShootTurret = new StopShootTurret();
  private TurretToZero turretToZero = new TurretToZero();

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
    new JoystickButton(controller, Button.kX.value)
      .whenHeld(oscillateTurret);
    new JoystickButton(controller, Button.kB.value)
      .whenPressed(lockOnTurret);
    new JoystickButton(controller, Button.kY.value)
      .whenPressed(turretToZero);
    new JoystickButton(controller, Button.kY.value)
      .whenPressed(shootTurret);
    new JoystickButton(controller, Button.kBumperRight.value)
      .whenHeld(stopShootTurret);
      /*
    new JoystickButton(controller, Button.kBumperLeft.value)
      .whenHeld(new RunCommand(turret::setWantedAngle(45) turret));
    new JoystickButton(controller, Button.kBumperRight.value)
      .whenHeld(new RunCommand(turret::setWantedAngle(-45), turret));
      */
      // controller.getYButtonPressed()
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
