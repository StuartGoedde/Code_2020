package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DeployIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  public DeployIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_Intake.PistonDeploy();
    m_Intake.isDeployed = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {return false;}
}
