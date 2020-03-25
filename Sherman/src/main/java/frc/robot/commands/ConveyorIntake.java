package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Harvester;

import java.sql.Timestamp;
import java.util.Date;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ConveyorIntake extends CommandBase {
  private Conveyor m_Conveyor = Conveyor.getInstance();
  
  private boolean inletBallDetected = false;
  private boolean done = false;
  public Timestamp calltime;

  public ConveyorIntake() {
    addRequirements(m_Conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    inletBallDetected = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean topLine = m_Conveyor.isTopLineClosed();
    boolean bottomLine = m_Conveyor.isBottomLineClosed();
    // System.out.print("Conveyor Intake | ");
    // System.out.print(calltime + " | ");
    // System.out.print("Top: " + topLine + " | ");
    // System.out.println("Bottom: " + bottomLine + " | ");
    // System.out.println(m_Conveyor.isTopLineClosed());
    if (m_Conveyor.isTopLineClosed()){
      if (!inletBallDetected) {
        if (!m_Conveyor.isBottomLineClosed()) {
          inletBallDetected = true;

          // m_Conveyor.setMotionMagic(10);
          m_Conveyor.setPercentControl(.2);
          // m_Harvester.setPercentControl(.25);
        }
      }
      if (inletBallDetected) {
        if (m_Conveyor.isBottomLineClosed()) {
          inletBallDetected = false;
          done = true;
          m_Conveyor.setPercentControl(0);
        }
      }
    } else {
      m_Conveyor.setPercentControl(0);
      // m_Harvester.setPercentControl(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Conveyor.setPercentControl(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
