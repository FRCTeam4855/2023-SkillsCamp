package frc.robot.Commands;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;

public class DriveUntilBalanced extends CommandBase {

    private final SwerveDriveSystem drivetrain;
    private AHRS gyro; // defines the gyro

    private boolean hasBeenUnbalanced = false;
    private final MedianFilter medianFilter = new MedianFilter(3);
    private final double driveSpeedMs;
    private double median = 0;
    private final boolean stopOnBridge;
    private final boolean invertAngles;

    public DriveUntilBalanced(
        SwerveDriveSystem drivetrain,
            double driveSpeedMs,
            boolean stopOnBridge, 
            AHRS gyro){
        this.drivetrain = drivetrain;
        this.driveSpeedMs = driveSpeedMs;
        this.stopOnBridge = stopOnBridge;
        invertAngles = false;
        this.gyro = gyro;
    }

    public DriveUntilBalanced(
            SwerveDriveSystem drivetrain,
            double driveSpeedMs,
            boolean stopOnBridge,
            boolean invertAngles,
            AHRS gyro){
        this.drivetrain = drivetrain;
        this.driveSpeedMs = driveSpeedMs;
        this.stopOnBridge = stopOnBridge;
        this.invertAngles = invertAngles;
        this.gyro = gyro;
    }

    public DriveUntilBalanced(SwerveDriveSystem driveSystem, AHRS gyro) {
        this(driveSystem, .3, true, false, gyro);
    }

    @Override
    public void initialize(){
        balancedTimestamp = 0;
        hasBeenUnbalanced = false;
        medianFilter.reset();
    }

    @Override
    public void execute() {
        System.out.println(gyro.getRoll());
        if(stopOnBridge){
            double currentDrive = hasBeenUnbalanced ? driveSpeedMs / 3.0 : driveSpeedMs;
            if(hasBeenUnbalanced){
                if(driveSpeedMs > 0 && ((gyro.getRoll() < 0 && !invertAngles) || (invertAngles && gyro.getRoll() > 0))){
                    currentDrive = - currentDrive / 1.5;
                }
                if(driveSpeedMs < 0 && ((gyro.getRoll() > 0 && !invertAngles) || (invertAngles && gyro.getRoll() < 0))){
                    currentDrive = - currentDrive / 1.5;
                }
            }

            if(median <= 10 && hasBeenUnbalanced){
                drivetrain.moveManual(0, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
                drivetrain.stop();
            } else {
                drivetrain.moveManual(currentDrive, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
            }
        } else {
            drivetrain.moveManual(driveSpeedMs, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
        }
    }

    double balancedTimestamp = 0;
    @Override
    public boolean isFinished(){
        median = medianFilter.calculate(Math.abs(gyro.getRoll()));

        if(median > 8){
            hasBeenUnbalanced = true;
        }

        if(median <= 2 && hasBeenUnbalanced && balancedTimestamp == 0){
            balancedTimestamp = Timer.getFPGATimestamp();
        } else if(median > 2){
            balancedTimestamp = 0;
        }

//        System.out.println("bt: " + balancedTimestamp + "ct:" + Timer.getFPGATimestamp());

        if(Timer.getFPGATimestamp() - balancedTimestamp > .2 && balancedTimestamp > 0){
            return median <= 2 && hasBeenUnbalanced;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted){
        drivetrain.moveManual(0, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
        drivetrain.stop();
    }
}
