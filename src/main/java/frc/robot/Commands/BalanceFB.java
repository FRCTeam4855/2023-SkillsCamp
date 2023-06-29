package frc.robot.Commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;

public class BalanceFB extends CommandBase {
        // private SwerveDriveSystem driveSystem;
        public static boolean isBalancing;
        private AHRS gyro; // defines the gyro
        private long balanceTime = -1;
        private double pitchAngleDegrees;
        private SwerveDriveSystem driveSystem;
        private double startTime;
    
        public BalanceFB(SwerveDriveSystem driveSystem, AHRS gyro) {
            this.driveSystem = driveSystem;
            this.gyro = gyro;
            pitchAngleDegrees = gyro.getPitch();
        };
    
        @Override
        public void initialize() {
            isBalancing = true;
            startTime = Timer.getFPGATimestamp();
            SmartDashboard.putBoolean("Balancing", isBalancing);
            SmartDashboard.putNumber("Balancing", gyro.getPitch());
        }
    
        @Override
        public void execute() {
            if (Math.abs( gyro.getPitch()) > 2) {
            driveSystem.moveManual(0, gyro.getPitch() / -30 +
            gyro.getPitch()/Math.abs( gyro.getPitch()) *-.1, 0, 0,
            Wheel.SpeedSetting.PRECISE);
            }
            SmartDashboard.putBoolean("Balancing", isBalancing);
            // if (Math.abs(gyro.getRoll()) > 2) {
            //     double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
            //     double yAxisRate = Math.sin(rollAngleRadians) * -1.8;
            //     driveSystem.moveManual(yAxisRate, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
            // }
            // SmartDashboard.putBoolean("Balancing", isBalancing);
            // SmartDashboard.putNumber("Balancing", gyro.getRoll());
        }
    
        @Override
        public boolean isFinished() {
            System.out.println("roll: " + gyro.getPitch());
            if (Math.abs(gyro.getPitch()) <= 2) {
                if (balanceTime < 0) {
                    balanceTime = System.currentTimeMillis();
                    if (System.currentTimeMillis() - balanceTime > 1000) {
                        driveSystem.stop(); // if balanced, start counting. Reset if becomes unbalanced. finished if able to
                                            // remain balanced for one full second.
                        isBalancing = false;
                        return true;
                    }
                }
            } else {
                balanceTime = -1;
            }
            isBalancing = true;
            return false;
    
            // if (Timer.getFPGATimestamp() - startTime > 5) {
            // return true;}
            // else {return false;}
            // }
    
        }
    
}
