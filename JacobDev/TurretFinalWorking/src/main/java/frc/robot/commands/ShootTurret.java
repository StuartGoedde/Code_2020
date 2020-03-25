/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.constants.TurretConstants;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Turret turret = Turret.getInstance();

  public ShootTurret() {
    addRequirements(turret);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    turret.setBoosterOutput(0.0);
    turret.setFlywheelOutput(0.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    turret.setBoosterOutput(TurretConstants.TURRET_MAX_BOOSTER_OUTPUT);
    turret.setFlywheelOutput(TurretConstants.TURRET_MAX_FLYWHEEL_OUTPUT);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }
}
