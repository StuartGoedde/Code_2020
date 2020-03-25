package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode; //Importing what we need from ctre in the Robot class

import frc.robot.MotorTest; //Imports our other files
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.ourpid;

public class Robot extends TimedRobot {
  //2nd number is the CAN ID and its creating them under the MotorTest Class
  private MotorTest talon1 = new MotorTest(1, 5);
  private MotorTest victor1 = new MotorTest(2, 0);

  private MotorTest[] motors = new MotorTest[2];

  private int motor_id = 0;
  private boolean enable = false;
  
  //Creates the Joystick for motorTestJoystick to use
  public Joystick Joy = new Joystick(0);

  // //Creates the controllers in ourpid class
  // public ourpid controller1 = new ourpid();
  // public ourpid controller0 = new ourpid(); 
  // public ourpid controller2 = new ourpid(); 

  // //Creates the controllers in ourpid class
  // public ourpid controller1 = new ourpid();
  // public ourpid controller0 = new ourpid(); 
  // public ourpid controller2 = new ourpid(); 
  /*
  Button 2 - Enable
  Trigger - Cycle Ids
  Slider - PercentOutput

  */
  
  // private ourpid[] controllers = new ourpid[3];

  private double Mpct;
  
  //Takes the slider value on the Joystick and gets a speed
  public double throtpct2speed(double throtpct){
    return 5775.6*(throtpct) + 5.1504; 
  }

  //Takes the speed and returns the percent for the motor to run
  public double speed2throt_pct(double speed){
    return 0.0002*(speed); 
  }

  @Override
  public void robotInit() {
    //Adding the sparks and talon to the motor list
    motors[0] = talon1;
    motors[1] = victor1;

    // //Sets the Encoder Velocity Conversion to 1.0
    // motors[0].getSpark().getEncoder().setVelocityConversionFactor(1.0);
    // motors[1].getSpark().getEncoder().setVelocityConversionFactor(1.0);

    // //Adds the motor controllers to the controllers list
    // controllers[0] = controller0;
    // controllers[1] = controller1;
    // controllers[2] = controller2;

    // //Sets what the PID Loop Values are going to be
    // double KP=0.01;
    // double KI=0.02;
    // double KD=0.0002;
    // for(int i=0;i<3;i++){
    //   controllers[i].setGains(KP, KI, KD);
    // }
  }
  
  @Override
  public void teleopPeriodic() {
    if(Joy.getRawButtonReleased(1)) {
      if(motor_id == 0) {
        motor_id = 1;
      } else {
        motor_id = 0;
      }
    } else if(Joy.getRawButtonReleased(2)) {
      if(enable == false) {
        enable = true;
      } else {
        enable = false;
      }
    } 
    Mpct= Joy.getThrottle()/2+1/2;

    // //Switches the controlled motor by pressing the trigger
    // Joy.switchMotor();
    
    // //Switches if the slider will change the speed of the motor
    // Joy.EnableThrottle();
      
    //Sets Mpct to the slider position
    // Mpct=Joy.ThrotAsPct();

    //Checks if the Throttle is enabled and if it is it sets the motor speed
    if(enable){
      // controllers[Joy.motor_id].setTargetOutput(throtpct2speed(Mpct));
      switch(motor_id) {
        case 0:
          motors[0].getTalon().set(ControlMode.PercentOutput, Mpct);
          break;
        case 1:
          motors[0].getVictor().set(Mpct);
          break;
      }
    }
    // controllers[0].setMeasuredOutput(motors[0].getSpark().getEncoder().getVelocity());
    // controllers[1].setMeasuredOutput(motors[1].getSpark().getEncoder().getVelocity());
    
    
    // motors[0].getSpark().set(controllers[0].calcControleEffort()+speed2throt_pct(controllers[0].target_output));
    // motors[2].getTalon().set(ControlMode.PercentOutput,speed2throt_pct(controllers[2].target_output)); //set SIMS motor to target speed as pct.
    
    // //Shows the Flywheel Values on the Smart Dashboard
    // SmartDashboard.putNumber("Flywheel Target",controllers[0].target_output);
    // SmartDashboard.putNumber("Flywheel Speed", controllers[0].measured_output);
    // SmartDashboard.putNumber("Flywheel Error", controllers[0].error);
    // SmartDashboard.putNumber("Flywheel Error", controllers[0].error_sum);
    // SmartDashboard.putNumber("Flywheel Error", controllers[0].error_difference);
    
    SmartDashboard.putNumber("Current Motor: ", motor_id);
    SmartDashboard.putBoolean("CTRL ENABLE", enable);    
  }
}

