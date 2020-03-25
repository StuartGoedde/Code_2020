package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.HarvesterConstants;

public class Harvester extends SubsystemBase {
  private static Harvester instance = new Harvester();

  private VictorSP motor = new VictorSP(HarvesterConstants.MOTOR_ID);
  private DoubleSolenoid leftPiston = new DoubleSolenoid(10, HarvesterConstants.LEFT_PISTON_ID_1, HarvesterConstants.LEFT_PISTON_ID_2);
  private DoubleSolenoid rightPiston = new DoubleSolenoid(10, HarvesterConstants.RIGHT_PISTON_ID_1, HarvesterConstants.RIGHT_PISTON_ID_2);

  public static Harvester getInstance() {
    return instance;
  }

  public void setPercentControl(double percent) {
    motor.set(percent);
  }

  public void deploy() {
    leftPiston.set(Value.kForward);
    rightPiston.set(Value.kForward);
  }

  public void retract() {
    leftPiston.set(Value.kReverse);
    rightPiston.set(Value.kReverse);
  }
}
