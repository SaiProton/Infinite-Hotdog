package frc.robot;

public final class Constants {
    //User Input Variables
    public static final int DRIVER_CONTROLLER = 0;
    public static final int TOOL_CONTROLLER = 1;

    public static final int L_STICK_Y = 1;
    public static final int R_STICK_X = 4;
    
    public static final int L_TRIGGER = 2;
    public static final int R_TRIGGER = 3;

    public static final int L_BUMPER = 5;
    public static final int R_BUMPER = 6;

    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;

    //Configuration Variables
    public static final double DEFAULT_SPD = 0.5;
    public static double MAX_SPEED = 0.5;

    //Robot Input Variables
    public static final int[] TURRET_LIMITS = {0, 0};

    public static final int[] INTAKE_LIMITS = {0, 0};

    public static final int[] CONVEYOR_OPTICALS = {0, 0};
    public static final int BALL_LAUNCH_OPTICAL = 0;
 
    public static final int DISTANCE_PWM = 0;
    
    //Robot Output Variables
    public static final int[] LEFT_MOTOR_CANS = {0, 0};
    public static final int[] RIGHT_MOTOR_CANS = {0, 0};

    public static final int[] SHOOTER_PWMs = {0, 0};
    public static final int FEEDER_PWM = 0;

    public static final int ROTATOR_PWM = 0;

    public static final int[] CONVEYOR_PWMs = {0, 0, 0};
    public static final int QUEUE_PWM = 0;

    public static final int INTAKE_MOVER = 0;
    public static final int INTAKE_INTAKER = 0;

    public static final int CLIMBER_PWM = 0;
}
