package frc.robot;

public class Constants {
    //Constants ++++++++++++
    public static final double JOYSTK_DZONE = .17;                  // global deadzone for all controllers
    public static final double DRIVE_DEFAULT_SPD = 0.50;               // multiplier
    public static final double DRIVE_SLOW_SPD = 0.15;               // multiplier
    public static final double DRIVE_TURBO_SPD = 0.90;               // multiplier



    // distance drive var
    public static final double ATON_DIST_ONE = 5;
    public static final double ATON_DIST_TWO = 5;

    public static final double DIST_BETWEEN_WHEELS = 27; // in inches  

    // Conversions +++++++++
    public static final double RELATIVE_ENC_TO_FT = 7.4; //0.660715;//7.928591; //0.03666658; // converts relative encoder units on neo to inches

    //Sensor Ranges/Setpoints

    //center point for arm extension setpoints
    public static final double ARM_EXTEND_CENTER_1 = 12; //done
    public static final double ARM_EXTEND_CENTER_2 = 180; 
    public static final double ARM_EXTEND_CENTER_3 = 100;
    public static final double  ARM_EXTEND_CENTER_4 = 333;
    public static final double ARM_EXTEND_CENTER_5 = 10;
    public static final double ARM_EXTEND_CENTER_6 = 5; //super high for auton
    public static final double ARM_EXTEND_SLOP = 5;    //acceptable range for arm extension setpoints

    //center point for arm extension setpoints
    public static final double ARM_PIVOT_CENTER_1 = 0; //done
    public static final double ARM_PIVOT_CENTER_2 = 8;
    public static final double ARM_PIVOT_CENTER_3 = 18.3;
    public static final double ARM_PIVOT_CENTER_4 = 21;
    public static final double ARM_PIVOT_CENTER_5 = 17;
    public static final double ARM_PIVOT_CENTER_6 = 24; //super high but for auton
    public static final double ARM_PIVOT_SLOP = .1;    //acceptable range for arm extension setpoints

    public enum ArmSetpoint {
        One, Two, Three, Four, Five, Six
    }

    //One = Interior bot, holding + traveling position
    //Two = floor pickup and place
    //Three = low cube
    //Four = Low Cone, High Cube, Player Window
    //Five = High Cone




    //@@@@@@@@@
    //COPY FROM
    // public class XboxController extends GenericHID {
    //     /** Represents a digital button on an XboxController. */
    //     public enum Button {
    //       kLeftBumper(5),
    //       kRightBumper(6),
    //       kLeftStick(9),
    //       kRightStick(10),
    //       kA(1),
    //       kB(2),
    //       kX(3),
    //       kY(4),
    //       kBack(7),
    //       kStart(8);
    //@@@@@@@@@@

    // Driver Mappings +++++
    public static final int GYRO_RESET_Y = 4; //Y
    public static final int ENCODER_RESET_B = 2; //B
    public static final int ORIENTATION_TOGGLE_X = 3; //X
    public static final int DRV_SPD_TURBO_RB = 6;
    public static final int DRV_SPD_PRECISE_LB = 5;   
    public static final int SCHEDULE_INITIAL_COMMAND_LB = 5;
    public static final int DRIVER_BALANCE_BCK = 7;

    
    
    
    
    
    
    //Operator Mappings ++++++
    public static final int INTAKE_FORWARD_RB = 6;
    public static final int INTAKE_BACKWARD_LB = 5;
    public static final int INTAKE_UP_X = 3; //X
    public static final int INTAKE_DOWN_Y = 4; //Y
}
