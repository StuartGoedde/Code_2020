package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Conveyor2;

public class Robot extends TimedRobot {
  public Joystick Joy;
  public Conveyor2 conveyor;

  @Override
  public void robotInit() {
    conveyor = new Conveyor2(); 
    Joy = new Joystick(1);
    } 

  @Override
  public void teleopPeriodic() {
    conveyor.getData();
    conveyor.pushData();

    if(Joy.getRawButtonPressed(1)) {
      conveyor.setData();
    }
    conveyor.advanceBelt();
  }
}