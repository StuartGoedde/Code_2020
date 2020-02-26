package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();
  private DoubleSupplier xSpeed;
  private DoubleSupplier rotSpeed;

  public ArcadeDrive(DoubleSupplier xSpeed, DoubleSupplier rotSpeed) {
    this.xSpeed = xSpeed;
    this.rotSpeed = rotSpeed;
    addRequirements(m_Drivetrain);
  }

  @Override
  public void execute() {
    m_Drivetrain.drive(new ChassisSpeeds(xSpeed.getAsDouble(), 0.0, rotSpeed.getAsDouble()));
  }
}
