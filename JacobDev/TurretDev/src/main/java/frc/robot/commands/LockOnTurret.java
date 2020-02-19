
package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class LockOnTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret = new OscillateTurret(turret);

  private boolean seesTarget;

  public LockOnTurret() {
    seesTarget = false;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(!seesTarget) {
      oscillateTurret.execute();
      seesTarget = SmartDashboard.getNumber("seesAG2", false);
    }
    else {
      visionAngle = SmartDashboard.getNumber("angle", 0);
      angleDiff = visionAngle + turret.getTurretAngle();
      turret.setTurretPosition(angleDiff / 360);
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
