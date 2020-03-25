package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  private double startTime;

  public SpinUpShooter() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
    // m_Shooter.setFlywheelVelocityControl(ShooterConstants.FLYWHEEL_VELOCITY);
    m_Shooter.setFlywheelPercentControl(ShooterConstants.FLYWHEEL_PERCENT_OUTPUT);
    m_Shooter.setBoosterPercentControl(ShooterConstants.BOOSTER_PERCENT_OUTPUT);
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    //m_Shooter.setFlywheelPercentControl(ShooterConstants.FLYWHEEL_PERCENT_OUTPUT);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(Timer.getFPGATimestamp() > startTime + ShooterConstants.FLYWHEEL_SPINUP_TIME){
      return true;//Spinning down the motor needs to be a separate command so it can come after the conveyor shoot
    } else {
      return false;
    }
  }
}
