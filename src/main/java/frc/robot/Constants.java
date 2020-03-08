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
    public static final int[] CONVEYOR_OPTICALS = {0, 1};
    public static final int BALL_LAUNCH_OPTICAL = 2;

    public static final int[] INTAKE_LIMITS = {6, 4};
    public static final int[] CLIMB_LIMITS = {5, 3};
     
    //Robot Output Variables
    public static final int[] LEFT_DRIVE_CANS = {6, 7};
    public static final int[] RIGHT_DRIVE_CANS = {9, 10};

    // public static final int[] SHOOTER_PWMs = {3, 4};
    public static final int FEEDER_PWM = 5;

    public static final int ROTATOR_PWM = 0;
  
    public static final int CONVEYOR_CAN = 5;
    public static final int INDEXER_CAN = 8;

    public static final int INTAKE_MOVER = 4;
    public static final int INTAKER_PWM = 9;

    public static final int CLIMB_ARM_PWM = 8;
    public static final int CLIMB_ROPE_PWM = 6;
}
