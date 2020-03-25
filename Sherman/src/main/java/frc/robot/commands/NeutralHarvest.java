package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Harvester;

public class NeutralHarvest extends CommandBase {
  private Harvester m_Harvester = Harvester.getInstance();

  public NeutralHarvest() {
    addRequirements(m_Harvester);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Harvester.setPercentControl(0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
