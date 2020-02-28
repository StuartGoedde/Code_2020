
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HomeTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret = new OscillateTurret();

  public HomeTurret() { }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    oscillateTurret.execute();
    isFinished();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(turret.isOnHallEffect()) {
        System.out.println("ON SENSOR");
        return true;
    }
    return false;
  }
}
