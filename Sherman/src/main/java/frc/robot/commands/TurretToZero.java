
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretToZero extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();

  public TurretToZero() { 
    addRequirements(turret);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    turret.setTurretPosition(turret.getMidPos());
  }

  @Override
  public void end(boolean interrupted) {
    turret.setTurretPercentOut(0);
  }

  @Override
  public boolean isFinished() {
    if(Math.abs(turret.getTurretAngle() - turret.getMidPos()) < 1) {
        return true;
    }
    return false;
  }
}
