package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TwoSecondDrive extends CommandBase {
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();
  private Double startTime = 0.0;

  public TwoSecondDrive() {
    addRequirements(m_Drivetrain);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }  

  @Override
  public void execute() {
    m_Drivetrain.drive(new ChassisSpeeds(-0.5, 0.0, 0.0));
  }

  @Override
  public void end(boolean interrupted) {
    m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
  }

  @Override
  public boolean isFinished() {
    if(Timer.getFPGATimestamp() > startTime + 2.0) {
      return true;
    } else {
      return false;
    }
  }
}
