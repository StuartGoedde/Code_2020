package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private static Climber instance = new Climber();

  private CANSparkMax motor = new CANSparkMax(ClimberConstants.MOTOR_ID, MotorType.kBrushless);
  private CANEncoder encoder = motor.getEncoder();
  private DigitalInput topLimit = new DigitalInput(ClimberConstants.TOP_LIMIT_PORT);
  private DigitalInput bottomLimit = new DigitalInput(ClimberConstants.BOTTOM_LIMIT_PORT);

  public Climber() {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSecondaryCurrentLimit(100);
    motor.burnFlash();
  }

  @Override
  public void periodic() {
    resetEncoder();
    putToDashboard();
  }

  public static Climber getInstance() {
    return instance;
  }

  public void setPercentControl(double percent) {
    motor.set(percent);
    SmartDashboard.putNumber("Percent Output", percent);
    SmartDashboard.putNumber("Amps", motor.getAppliedOutput());
  }

  public double getPostition() {
    return encoder.getPosition();
  }

  public boolean getTopLimit() {
    return topLimit.get();
  }

  public boolean getBottomLimit() {
    return bottomLimit.get();
  }

  public void resetEncoder() {
    encoder.setPosition(0);
  }

  public void putToDashboard() {
    SmartDashboard.putBoolean("Top Limit", topLimit.get());
    SmartDashboard.putBoolean("Bottom Limit", bottomLimit.get());
    SmartDashboard.putNumber("Position", encoder.getPosition());
  }
}