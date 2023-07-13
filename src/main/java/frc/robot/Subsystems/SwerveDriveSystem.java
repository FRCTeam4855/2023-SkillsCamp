package frc.robot.Subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveDriveSystem extends SubsystemBase implements GenericDriveSystem{
    
    private DigitalInput DIO10 = new DigitalInput(0); //Input on cRamageddon MXP 0-3 {10-13}
    private DigitalInput DIO11 = new DigitalInput(1);
    private DigitalInput DIO12 = new DigitalInput(2);
    private DigitalInput DIO13 = new DigitalInput(3);

    public Wheel wheelFL = new Wheel(1, 2, DIO10, -0.758); //defines the front left wheel //-0.225
    public Wheel wheelBL = new Wheel(3, 4, DIO11, -0.454); //defines the back left wheel //-0.053
    public Wheel wheelBR = new Wheel(5, 6, DIO12, -0.143); //defines the back right wheel //-0.337
    public Wheel wheelFR = new Wheel(7, 8, DIO13, -0.077); //defines the front right wheel //0.153
    //test drive platform
    // public Wheel wheelFL = new Wheel(1, 2, DIO10, -.3); //defines the front left wheel //-0.225
    // public Wheel wheelBL = new Wheel(3, 4, DIO11, .025); //defines the back left wheel //-0.053
    // public Wheel wheelBR = new Wheel(5, 6, DIO12, -.339); //defines the back right wheel //-0.337
    // public Wheel wheelFR = new Wheel(7, 8, DIO13, .147); //defines the front right wheel //0.153
    // // Wheel Values: driveControllerID, steerControllerID, absolutePort(encoder), offSet1

    //swervebot offsets:
    // public Wheel wheelFL = new Wheel(1, 2, DIO10, -0.225); //defines the front left wheel
    // public Wheel wheelBL = new Wheel(3, 4, DIO11, -0.053); //defines the back left wheel
    // public Wheel wheelBR = new Wheel(5, 6, DIO12, -0.337); //defines the back right wheel
    // public Wheel wheelFR = new Wheel(7, 8, DIO13, 0.153); //defines the front right wheel

    // private void moveWheels(SwerveOutput swerve) {
    //     wheelFL.set(swerve.wheelAngles[0], swerve.wheelSpeeds[0]); //grabs information from the arrays and feeds it to the wheels 
    //     wheelFR.set(swerve.wheelAngles[1], swerve.wheelSpeeds[1]);  
    //     wheelBR.set(swerve.wheelAngles[2], swerve.wheelSpeeds[2]); 
    //     wheelBL.set(swerve.wheelAngles[3], swerve.wheelSpeeds[3]);
    // }

    private void moveWheels(SwerveOutput swerve, Wheel.SpeedSetting speed) {
        wheelFL.set(swerve.wheelAngles[0], swerve.wheelSpeeds[0], speed); //grabs information from the arrays and feeds it to the wheels 
        wheelFR.set(swerve.wheelAngles[1], swerve.wheelSpeeds[1], speed);  
        wheelBR.set(swerve.wheelAngles[2], swerve.wheelSpeeds[2], speed); 
        wheelBL.set(swerve.wheelAngles[3], swerve.wheelSpeeds[3], speed);
    }

    private void stayWheels(SwerveOutput swerve, Wheel.SpeedSetting speed) {
        wheelFL.set(1.875, swerve.wheelSpeeds[0], speed); //grabs information from the arrays and feeds it to the wheels 
        wheelFR.set(-1.875, swerve.wheelSpeeds[1], speed);  
        wheelBR.set(1.875, swerve.wheelSpeeds[2], speed); 
        wheelBL.set(-1.875, swerve.wheelSpeeds[3], speed);
    }

    public double getEncoderFL() {
        return wheelFL.getDriveRelativeEncoderValue();
    }
    public double getEncoderBL() {
        return wheelBL.getDriveRelativeEncoderValue();
    }
    public double getEncoderBR() {
        return wheelBR.getDriveRelativeEncoderValue();
    }
    public double getEncoderFR() {
        return wheelFR.getDriveRelativeEncoderValue();
    }

    public double getAEncoderFL() {
        return wheelFL.getDriveRelativeEncoderValue();
    }
    public double getAEncoderBL() {
        return wheelBL.getDriveRelativeEncoderValue();
    }
    public double getAEncoderBR() {
        return wheelBR.getDriveRelativeEncoderValue();
    }
    public double getAEncoderFR() {
        return wheelFR.getDriveRelativeEncoderValue();
    }

    public void resetRelativeEncoders() {
      wheelFL.setRelativeEncoderToZero();
      wheelBL.setRelativeEncoderToZero();
      wheelBR.setRelativeEncoderToZero();
      wheelFR.setRelativeEncoderToZero();
    }

    public double getRelativeEncoderFT() {
        return Math.abs(wheelFL.getDriveRelativeEncoderValue()) / Constants.RELATIVE_ENC_TO_FT;
    }
    
    // public void moveVariable(double x1, double y1, double x2, double theta_radians) {
    //     SwerveOutput swerve = Swerve.convertControllerToSwerve(x1, y1, x2, theta_radians);
    //     this.moveWheels(swerve);
    // }
    
    public void moveVariable(double x1, double y1, double x2, double theta_radians, Wheel.SpeedSetting speed) {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(x1, y1, x2, theta_radians);
        this.moveWheels(swerve, speed);
    }

    @Override
    public void moveForward() { 
        SwerveOutput swerve = Swerve.convertControllerToSwerve(0, 1, 0, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }

    
    @Override
    public void moveBackward() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(0, -1, 0, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }
    @Override
    public void moveLeft() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(1, 0, 0, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }
    @Override
    public void moveRight() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(-1, 0, 0, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }
    @Override
    public void spinClockwise() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(0, 0, -1, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL); 
    }
    @Override
    public void spinCounterclockwise() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(0, 0, 1, 0);
        this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }

    @Override
    public void stop() {
        wheelFL.set(0,0); //grabs information from the arrays and feeds it to the wheels 
        wheelFR.set(0,0);  
        wheelBR.set(0,0); 
        wheelBL.set(0,0);
    }
    
    public void setStay() {
        SwerveOutput swerve = Swerve.convertControllerToSwerve(0, 0, 0, 0);
        this.stayWheels(swerve, Wheel.SpeedSetting.NORMAL);
    }

   @Override
    public void stay() {
            SwerveOutput swerve = Swerve.convertControllerToSwerve(0, 0, .001, 0);
            this.moveWheels(swerve, Wheel.SpeedSetting.NORMAL); 
        }
    

    public void moveVariable(double autox1, double autoy1, double autox2, int theta_radians, int i) {
    }
    
}
