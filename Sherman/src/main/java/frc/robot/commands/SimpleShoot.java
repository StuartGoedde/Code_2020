package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class SimpleShoot extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  private Conveyor m_Conveyor = Conveyor.getInstance();

  private BooleanSupplier upSupplier;
  private BooleanSupplier downSupplier;

  public SimpleShoot(BooleanSupplier upSupplier, BooleanSupplier downSupplier) {
    this.upSupplier = upSupplier;
    this.downSupplier = downSupplier;
    addRequirements(m_Conveyor);
  }

  @Override
  public void initialize() {
    m_Shooter.setBoosterPercentControl(1);
    m_Shooter.setFlywheelPercentControl(.8);
  }

  @Override
  public void execute() {
    m_Shooter.setFlywheelPercentControl(.8);
    m_Shooter.setBoosterPercentControl(1);
    
    if (upSupplier.getAsBoolean()) {
      m_Conveyor.setPercentControl(.3);
    } else if (downSupplier.getAsBoolean()) {
      m_Conveyor.setPercentControl(-.3);
    } else {
      m_Conveyor.setPercentControl(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Conveyor.setPercentControl(0);
    m_Shooter.setFlywheelPercentControl(0);
    m_Shooter.setBoosterPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
