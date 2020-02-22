/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ClimberWinch;

public class Climb extends CommandBase {
  private ClimberWinch m_ClimberWinch = ClimberWinch.getInstance();

  /**
   * Creates a new Climb.
   */
  public Climb() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ClimberWinch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ClimberWinch.setClimberPostition(Constants.climbHeight);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_ClimberWinch.getClimberPosition() == Constants.climbHeight){
      return true;
    } else {
      return false;
    }
  }
}
