/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private WPI_TalonFX flywheel = new WPI_TalonFX(5);
  private VictorSP booster = new VictorSP(0);
  private TalonSRX conveyorMaster = new TalonSRX(3);
  private TalonSRX conveyorFollower = new TalonSRX(33);
  private Joystick stick = new Joystick(0);

  @Override
  public void robotInit() {
    SmartDashboard.putNumber("Shooter Percent", 0);
    // flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 20);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if (stick.getRawButton(2)) {
      conveyorMaster.set(ControlMode.PercentOutput, .3);
      conveyorFollower.follow(conveyorMaster);
      booster.set(1);
      flywheel.set(SmartDashboard.getNumber("Shooter Percent", 0));
    } else if (stick.getRawButton(1)) {
      conveyorMaster.set(ControlMode.PercentOutput, 0);
      conveyorFollower.follow(conveyorMaster);
      booster.set(1);
      flywheel.set(SmartDashboard.getNumber("Shooter Percent", 0));
    } else {
      conveyorMaster.set(ControlMode.PercentOutput, 0);
      conveyorFollower.follow(conveyorMaster);
      booster.set(0);
      flywheel.set(0);
    }
    SmartDashboard.putNumber("RPM", flywheel.getSelectedSensorVelocity());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
