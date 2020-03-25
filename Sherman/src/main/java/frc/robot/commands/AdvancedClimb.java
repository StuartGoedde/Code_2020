package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.constants.ClimberConstants;

public class AdvancedClimb extends CommandBase {
  private Climber m_Climber = Climber.getInstance();
  private DoubleSupplier supplier;

  private double currentClimbHeight;

  public AdvancedClimb(DoubleSupplier supplier) {
    this.supplier = supplier;
    addRequirements(m_Climber);
  }

  @Override
  public void initialize() {
    currentClimbHeight = m_Climber.getPostition();
  }

  @Override
  public void execute() {
    //Update SD
    currentClimbHeight = m_Climber.getPostition();
    SmartDashboard.putNumber("Climb encoder(tics)", currentClimbHeight);

    //Zero if we're at the bottom limit switch 
    if(!m_Climber.getBottomLimit()) {
      m_Climber.resetEncoder();
    }
    
    //Don't let the driver go past the switches
    if(!m_Climber.getTopLimit() && supplier.getAsDouble() < 0) {
      m_Climber.setPercentControl(0.0);
    } 
    else if(!m_Climber.getBottomLimit() && supplier.getAsDouble() > 0) {
      m_Climber.setPercentControl(0.0);
    } else {
      m_Climber.setPercentControl(supplier.getAsDouble());
    }
    /*
    if ((!m_Climber.getTopLimit() && supplier.getAsDouble() < 0) || (!m_Climber.getBottomLimit() && supplier.getAsDouble() > 0)) {
      if (m_Climber.getPostition() < ClimberConstants.CLIMB_HEIGHT * .9) {
        m_Climber.setPercentControl(supplier.getAsDouble());
      } else if (m_Climber.getPostition() < ClimberConstants.CLIMB_HEIGHT * .9) {
        m_Climber.setPercentControl(supplier.getAsDouble() * 0.1);
      }
    } else {
      m_Climber.setPercentControl(0);
    }
    */
  }

  @Override
  public void end(boolean interrupted) {
    m_Climber.setPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
