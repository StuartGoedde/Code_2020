/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class FarShootAndDrive extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  private Conveyor m_Conveyor = Conveyor.getInstance();
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  private double startTime = Timer.getFPGATimestamp();

  public FarShootAndDrive() {
    addRequirements(m_Drivetrain, m_Shooter, m_Conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
    m_Shooter.setBoosterPercentControl(1);
    m_Shooter.setFlywheelPercentControl(0.8);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Shooter.setBoosterPercentControl(1.0);
    m_Shooter.setFlywheelPercentControl(0.8);
    if (Timer.getFPGATimestamp() < startTime + 3.0) {
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
      m_Conveyor.setPercentControl(0.0);
    } else if (Timer.getFPGATimestamp() < startTime + 10.0) {
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
      m_Conveyor.setPercentControl(0.2);
    } else if (Timer.getFPGATimestamp() < startTime + 11.0) {
      m_Drivetrain.drive(new ChassisSpeeds(0.5, 0.0, 0.0));
      m_Conveyor.setPercentControl(0.0);
    } else {
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
      m_Conveyor.setPercentControl(0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    m_Conveyor.setPercentControl(0.0);
    m_Shooter.setBoosterPercentControl(0.0);
    m_Shooter.setFlywheelPercentControl(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Timer.getFPGATimestamp() > startTime + 14) {
      return true;
    } else {
      return false;
    }
  }
}
