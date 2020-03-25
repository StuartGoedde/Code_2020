package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class NeutralClimb extends CommandBase {
  private Climber m_Climber = Climber.getInstance();

  public NeutralClimb() {
    addRequirements(m_Climber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Climber.setPercentControl(0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
