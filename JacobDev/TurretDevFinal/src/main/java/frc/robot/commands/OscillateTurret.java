
package frc.robot.commands;

import frc.robot.constants.TurrentConstants;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OscillateTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();

  private boolean isTurningRight;
  private boolean finished = false;

  public OscillateTurret() {
    addRequirements(turret);
    isTurningRight = true;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(isTurningRight) {
        //Go to the maxPos
        turret.setTurretPosition(turret.getMaxPos());
        //Turret is less than the maxPos so turn around
        if(turret.getTurretAngle() > (turret.getMaxPos() - 5.0)) {
            isTurningRight = false;
        }
    } else {    
        //Go to the minPos
        turret.setTurretPosition(turret.getMinPos());
        //Turret is greater than the minPos so turn around
        if(turret.getTurretAngle() < (turret.getMinPos() + 5.0)) {
            isTurningRight = true;
        }
    }
    turret.calibratePosition();
  }

  @Override
  public void end(boolean interrupted) {
    turret.setTurretPercentOut(0);
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
}
