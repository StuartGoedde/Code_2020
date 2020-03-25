package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.HarvesterConstants;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Conveyor;;

public class Sow extends CommandBase {
  private Harvester m_Harvester = Harvester.getInstance();
  private Conveyor m_Conveyor = Conveyor.getInstance();

  public Sow() {
    addRequirements(m_Harvester);
  }

  @Override
  public void initialize() {
    m_Harvester.setPercentControl(1);
    m_Conveyor.setPercentControl(-0.45);
  }

  @Override
  public void execute() {
    m_Harvester.setPercentControl(1);
    m_Conveyor.setPercentControl(-0.45);
  }

  @Override
  public void end(boolean interrupted) {
    m_Harvester.setPercentControl(0);
    m_Conveyor.setPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
