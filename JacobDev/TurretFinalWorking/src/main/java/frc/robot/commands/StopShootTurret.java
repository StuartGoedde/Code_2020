
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopShootTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();

  private boolean stop = false;

  public StopShootTurret() { 
    addRequirements(turret);
    stop = true;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    turret.setBoosterOutput(0.0);
    turret.setFlywheelOutput(0.0);
  }

  @Override
  public boolean isFinished() {
    if(stop) {
        return true;
    }
    return false;
  }
}
