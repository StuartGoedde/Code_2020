package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class SimpleClimb extends CommandBase {
  private Climber m_Climber = Climber.getInstance();
  private DoubleSupplier supplier;

  public SimpleClimb(DoubleSupplier supplier) {
    this.supplier = supplier;
    addRequirements(m_Climber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Climber.setPercentControl(supplier.getAsDouble());
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
