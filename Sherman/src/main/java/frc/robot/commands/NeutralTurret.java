package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class NeutralTurret extends CommandBase {
  private Turret m_Turret = Turret.getInstance();

  public NeutralTurret() {
    addRequirements(m_Turret);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Turret.setTurretPercentOut(0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
