/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.DrivetrainConstants;
// import frc.robot.Controller;
// import frc.robot.VisionClient;

public class Drivetrain extends SubsystemBase {
  private static Drivetrain instance = new Drivetrain();

  private final DoubleSolenoid rightShifter = new DoubleSolenoid(10, DrivetrainConstants.RIGHT_SHIFTER_ID_1,
      DrivetrainConstants.RIGHT_SHIFTER_ID_2);
  private final DoubleSolenoid leftShifter = new DoubleSolenoid(10, DrivetrainConstants.LEFT_SHIFTER_ID_1, DrivetrainConstants.LEFT_SHIFTER_ID_2);

  private final WPI_TalonFX leftMaster = new WPI_TalonFX(DrivetrainConstants.LEFT_MASTER_ID);
  private final WPI_TalonFX leftFollower = new WPI_TalonFX(DrivetrainConstants.LEFT_FOLLOWER_ID);
  private final WPI_TalonFX rightMaster = new WPI_TalonFX(DrivetrainConstants.RIGHT_MASTER_ID);
  private final WPI_TalonFX rightFollower = new WPI_TalonFX(DrivetrainConstants.RIGHT_FOLLOWER_ID);

  private final AHRS gyro = new AHRS(SPI.Port.kMXP);

  private final SimpleMotorFeedforward leftFeedforwardHigh = new SimpleMotorFeedforward(
      DrivetrainConstants.LEFT_FF[1][0], DrivetrainConstants.LEFT_FF[1][1], DrivetrainConstants.LEFT_FF[1][2]);
  private final SimpleMotorFeedforward rightFeedforwardHigh = new SimpleMotorFeedforward(
      DrivetrainConstants.RIGHT_FF[1][0], DrivetrainConstants.RIGHT_FF[1][1], DrivetrainConstants.RIGHT_FF[1][2]);
  private final PIDController leftPIDControllerHigh = new PIDController(DrivetrainConstants.LEFT_PID[1][0],
      DrivetrainConstants.LEFT_PID[1][1], DrivetrainConstants.LEFT_PID[1][2]);
  private final PIDController rightPIDControllerHigh = new PIDController(DrivetrainConstants.RIGHT_PID[1][0],
      DrivetrainConstants.RIGHT_PID[1][1], DrivetrainConstants.RIGHT_PID[1][2]);

  private final SimpleMotorFeedforward leftFeedforwardLow = new SimpleMotorFeedforward(
      DrivetrainConstants.LEFT_FF[0][0], DrivetrainConstants.LEFT_FF[0][1], DrivetrainConstants.LEFT_FF[0][2]);
  private final SimpleMotorFeedforward rightFeedforwardLow = new SimpleMotorFeedforward(
      DrivetrainConstants.RIGHT_FF[0][0], DrivetrainConstants.RIGHT_FF[0][1], DrivetrainConstants.RIGHT_FF[0][2]);
  private final PIDController leftPIDControllerLow = new PIDController(DrivetrainConstants.LEFT_PID[0][0],
      DrivetrainConstants.LEFT_PID[0][1], DrivetrainConstants.LEFT_PID[0][2]);
  private final PIDController rightPIDControllerLow = new PIDController(DrivetrainConstants.RIGHT_PID[0][0],
      DrivetrainConstants.RIGHT_PID[0][1], DrivetrainConstants.RIGHT_PID[0][2]);

  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
      DrivetrainConstants.TRACK_WIDTH);
  private final DifferentialDriveOdometry odometry;

  private boolean inHighGear = true; //should this be a boolean (It definitely should, but is it annoying to refactor?)

  public Drivetrain() {
    gyro.reset();
    odometry = new DifferentialDriveOdometry(getAngle());
    rightMaster.setInverted(true);
    //rightShifter.set(Value.kReverse);
    //leftShifter.set(Value.kReverse);
    rightShifter.set(Value.kForward);
    leftShifter.set(Value.kForward);
    rightFollower.follow(rightFollower);
    leftFollower.follow(leftMaster);
    rightMaster.configOpenloopRamp(DrivetrainConstants.OPEN_LOOP_RAMP_RATE);
    rightFollower.configOpenloopRamp(DrivetrainConstants.OPEN_LOOP_RAMP_RATE);
    leftMaster.configOpenloopRamp(DrivetrainConstants.OPEN_LOOP_RAMP_RATE);
    leftFollower.configOpenloopRamp(DrivetrainConstants.OPEN_LOOP_RAMP_RATE);
  }

  @Override
  public void periodic() {
    updateOdometry();
    printOdometry();
    getInHighGear();
  } //Comenttttt

  public static Drivetrain getInstance() {
    return instance;
  }

  // public void arcadeDrive(double speed, double rot) {
  
  // }

  public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
    System.out.println("Right Speed: " + speeds.rightMetersPerSecond + " | Left Speed: " + speeds.leftMetersPerSecond);
    double leftOutput = speeds.leftMetersPerSecond;
    double rightOutput = speeds.rightMetersPerSecond;
    
    // if (inHighGear) {
    //   leftOutput = leftPIDControllerHigh.calculate(
    //       leftMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, speeds.leftMetersPerSecond)
    //       + leftFeedforwardHigh.calculate(speeds.leftMetersPerSecond);
    //   rightOutput = rightPIDControllerHigh.calculate(
    //       rightMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, speeds.rightMetersPerSecond)
    //       + rightFeedforwardHigh.calculate(speeds.rightMetersPerSecond);
    // } else {
    //   leftOutput = leftPIDControllerLow.calculate(
    //       leftMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, speeds.leftMetersPerSecond)
    //       + leftFeedforwardLow.calculate(speeds.leftMetersPerSecond);
    //   rightOutput = rightPIDControllerLow.calculate(
    //       rightMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, speeds.rightMetersPerSecond)
    //       + rightFeedforwardLow.calculate(speeds.rightMetersPerSecond);
    // }

    // leftMaster.setVoltage(leftOutput);
    // rightMaster.setVoltage(rightOutput);
    // leftFollower.follow(leftMaster);
    // rightFollower.follow(rightMaster);
    leftMaster.set(leftOutput);
    rightMaster.set(rightOutput);
  }

  public void setSpeedsDouble(double leftMetersPerSecond, double rightMetersPerSecond) {
    double leftOutput;
    double rightOutput;
    
    if (inHighGear) {
      leftOutput = leftPIDControllerHigh.calculate(
          leftMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, leftMetersPerSecond)
          + leftFeedforwardHigh.calculate(leftMetersPerSecond);
      rightOutput = rightPIDControllerHigh.calculate(
          rightMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, rightMetersPerSecond)
          + rightFeedforwardHigh.calculate(rightMetersPerSecond);
    } else {
      leftOutput = leftPIDControllerLow.calculate(
          leftMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, leftMetersPerSecond)
          + leftFeedforwardLow.calculate(leftMetersPerSecond);
      rightOutput = rightPIDControllerLow.calculate(
          rightMaster.getSelectedSensorVelocity() * DrivetrainConstants.VELOCITY_RATIO, rightMetersPerSecond)
          + rightFeedforwardLow.calculate(rightMetersPerSecond);
    }

    leftMaster.setVoltage(leftOutput);
    rightMaster.setVoltage(rightOutput);
    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);
  }

  @SuppressWarnings("ParameterName")
  public void drive(ChassisSpeeds m_chassisSpeeds) {
    System.out.println("Linear Speed: " + m_chassisSpeeds.vxMetersPerSecond +" | Rot Speed: " + m_chassisSpeeds.omegaRadiansPerSecond);
    var wheelSpeeds = kinematics.toWheelSpeeds(m_chassisSpeeds);
    setSpeeds(wheelSpeeds);
  }

  public void updateOdometry() {
    odometry.update(getAngle(), leftMaster.getSelectedSensorPosition() * DrivetrainConstants.POSITION_RATIO,
        rightMaster.getSelectedSensorPosition() * DrivetrainConstants.POSITION_RATIO);
  }

  public void printOdometry() {
    SmartDashboard.putNumber("X Pos", odometry.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("Y Pos", odometry.getPoseMeters().getTranslation().getY());
    SmartDashboard.putNumber("Rot Pos", odometry.getPoseMeters().getRotation().getDegrees());
  }

  public void motorMonitoring() {
    SmartDashboard.putNumber("left draw", leftMaster.getSupplyCurrent());
    SmartDashboard.putNumber("left out", leftMaster.getStatorCurrent());
    SmartDashboard.putNumber("right draw", rightMaster.getSupplyCurrent());
    SmartDashboard.putNumber("right out", rightMaster.getStatorCurrent());
  }

  public void resetOdometry() {
    odometry.resetPosition(new Pose2d(), getAngle());
    rightMaster.setSelectedSensorPosition(0);
    leftMaster.setSelectedSensorPosition(0);
  }

  public void setPose(Pose2d setPose) {
    odometry.resetPosition(setPose, getAngle());
    rightMaster.setSelectedSensorPosition(0);
    leftMaster.setSelectedSensorPosition(0);
  }

  public Rotation2d getAngle() {
    return Rotation2d.fromDegrees(-gyro.getAngle());
  }

  public Pose2d getCurrentPose() {
    return odometry.getPoseMeters();
  }

  public boolean getInHighGear() {
    SmartDashboard.putBoolean("Gear", !inHighGear);
    return inHighGear;
  }

  public void setInHighGear(boolean inHighGear) {
    this.inHighGear = inHighGear;
  }

  public void shift() {
    if (rightShifter.get() == DoubleSolenoid.Value.kReverse) {
      rightShifter.set(DoubleSolenoid.Value.kForward);
      leftShifter.set(DoubleSolenoid.Value.kForward);
      inHighGear = true;
    } else {
      rightShifter.set(DoubleSolenoid.Value.kReverse);
      leftShifter.set(DoubleSolenoid.Value.kReverse);
      inHighGear = false;
    }
  }

  public DifferentialDriveKinematics getKinematics() {
    return kinematics;
  }
}
