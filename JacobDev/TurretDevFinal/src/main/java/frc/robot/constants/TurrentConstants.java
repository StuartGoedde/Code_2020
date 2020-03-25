package frc.robot.constants;

public class TurrentConstants {
    public static int HALL_EFFECT_SENSOR_ID = 0;

    public static int ROTATE_MOTOR_ID = 33;
    public static int FLYWHEEL_ID = 22;
    public static int BOOSTER_ID = 11;


    public static double ROTATE_PID_P = 0.0175;
    public static double ROTATE_PID_I = 0.0001;
    public static double ROTATE_PID_D = 0.001;
    //public static double FLYWHEEL_P = 0.01;
    //public static double FLYWHEEL_I = 0.02;
    //public static double FLYWHEEL_D = 0.0002;

    public static int FLYWHEEL_VELOCITY = 0;

    public static double TURRENT_MIN_ANGLE = -88.0;
    public static double TURRENT_MID_ANGLE = 4.0;
    public static double TURRENT_MAX_ANGLE = 94.0;

    public static final double TURRET_MAX_ROTATION_OUTPUT = 0.1;
    public static final double TURRET_MAX_BOOSTER_OUTPUT = 0.1; //1.0
    public static final double TURRET_MAX_FLYWHEEL_OUTPUT = 0.08; //0,8

    //RATIOS
    public static final double TURRET_TO_MOTOR_GEAR_RATIO = (double)120/16;
    public static final double MOTOR_TO_TURRET_GEAR_RATIO = (double)16/120;

    //ENCODDER CONSTANTS
    public static final double ENCODER_TICKS_PER_REVOLUTION = 4096.0;
    public static final double REVOLUTIONS_PER_ENCODER_TICK = 1.0 / 4096.0;

}
