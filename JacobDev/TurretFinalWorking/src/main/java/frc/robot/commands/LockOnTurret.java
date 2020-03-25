
package frc.robot.commands;

import frc.robot.VisionClient;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class LockOnTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret;
  private VisionClient m_VisionClient = VisionClient.getInstance();

  private boolean seesTarget;
  private double visionAngle;
  private double setPoint;

  public LockOnTurret(DoubleSupplier stick, BooleanSupplier button) {
    addRequirements(turret);
    seesTarget = false;
    oscillateTurret = new OscillateTurret();
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    seesTarget = m_VisionClient.seesG2();
    visionAngle = m_VisionClient.getAngle2G2();
    if(!seesTarget) {
      oscillateTurret.execute();
      //setPoint = turret.getTurretAngle();
      SmartDashboard.putBoolean("Working", false);
    }
    else {
      SmartDashboard.putBoolean("Working", true);
      setPoint = visionAngle + turret.getTurretAngle();
      SmartDashboard.putNumber("angle", visionAngle);
      SmartDashboard.putNumber("setpoint", setPoint);
      turret.setTurretPosition(setPoint);
    }
  }

  @Override
  public void end(boolean interrupted) {
    turret.setTurretPercentOut(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
