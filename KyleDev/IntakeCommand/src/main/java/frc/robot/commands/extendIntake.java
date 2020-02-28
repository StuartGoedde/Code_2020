package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class extendIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  boolean deployed = false;
  boolean done=false;
  
  public extendIntake() {
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {
    deployed=false;
    done= false;
  }

  @Override
  public void execute() {
    if (!deployed){
    m_Intake.PistonDeploy();
    m_Intake.StartIntake(Constants.intakeMaxPCT);
    deployed = true;
    }
    done=true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(done){
      return true;
    }
    return false;
  }
}