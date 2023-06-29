package frc.robot.Commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;

public class CenterToLimelight <LimelightDriveSystem> extends CommandBase {
    
    double Kp = -0.05;
    double min_command = 0.05;
    double heading_setpoint = 3;
    double initTime;
    double heading_error;
    private Limelight limelight;
    private SwerveDriveSystem swerveDriveSystem;

    public CenterToLimelight(Limelight limelight, SwerveDriveSystem swerveDriveSystem) {
        this.limelight = limelight;
        this.swerveDriveSystem = swerveDriveSystem;
    }

    @Override
    public void initialize() {
        limelight.setLimelightLampOn();
        limelight.setLimelightPipeToAprilTag();
        NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("ledMode").setNumber(3);
    }

    @Override
    public void execute() {
        double tx = NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("tx").getDouble(0);
        heading_error = -tx;
        if (Math.abs(heading_error) > heading_setpoint) {
                swerveDriveSystem.moveManual(Kp * heading_error + min_command, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
        }
    }

    @Override
    public boolean isFinished() { // specifies end conditions
        if (Math.abs(heading_error) < heading_setpoint) {
            swerveDriveSystem.stop();
            return true;
        }
        return false;
    }
}