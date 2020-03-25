
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.TurrentConstants;

public class Turret extends SubsystemBase {

    private static Turret instance = new Turret();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(TurrentConstants.HALL_EFFECT_SENSOR_ID);

    //Motors (with CAN IDs)
    private TalonSRX rotateMotor = new TalonSRX(TurrentConstants.ROTATE_MOTOR_ID); 
    private WPI_TalonFX flywheelMotor = new WPI_TalonFX(TurrentConstants.FLYWHEEL_ID);
    private TalonSRX boosterMotor = new TalonSRX(TurrentConstants.BOOSTER_ID);

    //Measured in Degrees
    private double minPos = TurrentConstants.TURRENT_MIN_ANGLE; //-88
    private double midPos = TurrentConstants.TURRENT_MID_ANGLE; //Degrees have been reduced to avoid hardstops
    private double maxPos = TurrentConstants.TURRENT_MAX_ANGLE; //92

    //Measurements
    private final double degrees2Rotations = (double)1 / 360; 

    private PIDController pid = new PIDController(TurrentConstants.ROTATE_PID_P, TurrentConstants.ROTATE_PID_I, TurrentConstants.ROTATE_PID_D);

    private double wantedAngle = 0.0;
    private double currentAngle = 0.0;
    private double lastAngle = 0.0;
    private boolean currentHall = false;
    private boolean lastHall = false;

    public Turret() {
      setBoosterOutput(0.0);
      setFlywheelOutput(0.0);
      setTurretAngle((int)midPos);
    }

    @Override
    public void periodic() {
        getFlywheelOutput();
        isOnHallEffect();
        getTurretAngle();
    }

    public static Turret getInstance() {
        return instance;
    }

    public double getFlywheelOutput() {
      double output = flywheelMotor.getMotorOutputPercent();
      SmartDashboard.putNumber("flyWheel (%)Output", output);
      return output;
    }

    public void setFlywheelOutput(double percentOutput) {
      flywheelMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    public void setBoosterOutput(double percentOutput) {
      boosterMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    public double getTurretPosition() {
        return rotateMotor.getSelectedSensorPosition();    // <-- Gets selected sensor position in _raw_ sensor units.  "0" for Primary closed-loop, "1" for auxilliary closed-loop.
    }

    public void setTurretPosition(double wantedAngle) {
        this.wantedAngle = wantedAngle;
        if(wantedAngle > maxPos) {
            wantedAngle = maxPos;
        } else if(wantedAngle < minPos) {
            wantedAngle = minPos;
        }
        SmartDashboard.putNumber("Wanted Angle", wantedAngle);
        currentAngle = getTurretAngle();
        double output = -1 * pid.calculate(currentAngle, wantedAngle);
        // if (Math.abs(error) > 90) {
        if (output > TurrentConstants.TURRET_MAX_ROTATION_OUTPUT) {
            output = TurrentConstants.TURRET_MAX_ROTATION_OUTPUT;
        } else if (output < -TurrentConstants.TURRET_MAX_ROTATION_OUTPUT) {
            output = -TurrentConstants.TURRET_MAX_ROTATION_OUTPUT;
        }
        SmartDashboard.putNumber("Output", output);
        rotateMotor.set(ControlMode.PercentOutput, output);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(ControlMode.PercentOutput, percent);
        calibratePosition();
    }

    public void setTurretAngle(int angle) {
        double rawAngle2Tics = angle / (TurrentConstants.MOTOR_TO_TURRET_GEAR_RATIO * TurrentConstants.REVOLUTIONS_PER_ENCODER_TICK * 360);
        int rawTics = (int) rawAngle2Tics;
        rotateMotor.setSelectedSensorPosition(rawTics);    
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        double angle = -1.0 * getTurretPosition() * TurrentConstants.MOTOR_TO_TURRET_GEAR_RATIO * TurrentConstants.REVOLUTIONS_PER_ENCODER_TICK * 360.0;
        SmartDashboard.putNumber("Actual Angle", currentAngle);
        return angle;
    }

    public double getMinPos() { return minPos; }
    public double getMidPos() { return midPos; }
    public double getMaxPos() { return maxPos; }

    //Hall effect sensors are true when the magnet is not near, so it's inverted to be intuitive again
    public boolean isOnHallEffect() { 
        SmartDashboard.putBoolean("Hall Effect", !hallEffect.get());
        return !hallEffect.get();
    }

    public void calibratePosition() {
      currentAngle = getTurretAngle();
      currentHall = isOnHallEffect();

      if(((currentAngle - lastAngle) > 0 && (lastHall == false && currentHall == true)) || 
      ((currentAngle - lastAngle) < 0 && (lastHall == true && currentHall == false))) { 
          rotateMotor.setSelectedSensorPosition(0);
          System.out.println("true");
      }
      lastAngle = currentAngle;
      lastHall = currentHall;
    }
}