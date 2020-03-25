package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private static Shooter instance = new Shooter();

  public WPI_TalonFX flywheel = new WPI_TalonFX(ShooterConstants.FLYWHEEL_ID);
  public VictorSP booster = new VictorSP(ShooterConstants.BOOSTER_ID);

  public Shooter() {
    // flywheel.config_kP(0, ShooterConstants.FLYWHEEL_P);
    // flywheel.config_kI(0, ShooterConstants.FLYWHEEL_I);
    // flywheel.config_kD(0, ShooterConstants.FLYWHEEL_D);
  }

  public static Shooter getInstance() {
    return instance;
  }

  public void setFlywheelVelocityControl(double velocity) {
    // flywheel.set(ControlMode.Velocity, velocity);
  }

  public void setFlywheelPercentControl(double percent) {
    flywheel.set(percent);
  }
  
  public void setBoosterPercentControl(double percent) {
    booster.set(percent);
  }

  public double getFlywheelSpeed(){
    return flywheel.getSelectedSensorVelocity();
  }
}
