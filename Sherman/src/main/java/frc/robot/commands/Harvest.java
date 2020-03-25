package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.HarvesterConstants;
import frc.robot.subsystems.Harvester;

public class Harvest extends CommandBase {
  private Harvester m_Harvester = Harvester.getInstance();

  public Harvest() {
    addRequirements(m_Harvester);
  }

  @Override
  public void initialize() {
    m_Harvester.deploy();
    m_Harvester.setPercentControl(HarvesterConstants.HARVEST_PERCENT);
  }

  @Override
  public void execute() {
    m_Harvester.setPercentControl(HarvesterConstants.HARVEST_PERCENT);
  }

  @Override
  public void end(boolean interrupted) {
    m_Harvester.retract();
    m_Harvester.setPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
