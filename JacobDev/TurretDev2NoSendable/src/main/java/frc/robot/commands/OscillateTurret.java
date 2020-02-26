
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OscillateTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();

  private final String name = "oscillateTurret";

  private boolean isTurningRight;

  public OscillateTurret() {
    isTurningRight = true;
  }

  public String getName() {
    return name;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(isTurningRight) {
        System.out.println("RIGHT");
        //Go to the minPos
        turret.setTurretPosition(turret.getMinPos());
        //Turret is less than the minPos so turn around
        if(turret.getTurretAngle() < turret.getMinPos()) {
            isTurningRight = false;
        }
        
    } else {
        System.out.println("LEFT");
        //Go to the minPos
        turret.setTurretPosition(turret.getMaxPos());
        //Turret is greater than the maxPos so turn around
        if(turret.getTurretAngle() > turret.getMaxPos()) {
            isTurningRight = true;
        }
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
