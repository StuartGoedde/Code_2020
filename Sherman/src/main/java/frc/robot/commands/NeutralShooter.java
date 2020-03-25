package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class NeutralShooter extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();

  public NeutralShooter() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Shooter.setBoosterPercentControl(0);
    m_Shooter.setFlywheelPercentControl(0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
