/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.constants.TurrentConstants;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StopShootTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Turret turret = Turret.getInstance();

  private boolean done = false;

  public StopShootTurret() {
    addRequirements(turret);
    done = true;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {}
  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    if(done) {
        return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    turret.setBoosterOutput(0.0);
    turret.setFlywheelOutput(0.0);
  }
}
