package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SpinDownShooter extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  private double startTime;

  public SpinDownShooter() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
    m_Shooter.setFlywheelPercentControl(0);
    m_Shooter.setBoosterPercentControl(0);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
      return false;
  }
}
