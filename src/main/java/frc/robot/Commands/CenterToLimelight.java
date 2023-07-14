package frc.robot.Commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;
import edu.wpi.first.wpilibj.Timer;

public class CenterToLimelight extends CommandBase {

    double Kp = -0.05;
    double min_command = 0.2;
    double heading_setpoint = 3;
    double initTime;
    double heading_error;
    private long strafeTime = -1;
    private double startTime;
    private final Limelight limelight;
    private final SwerveDriveSystem swerveDriveSystem;

    public CenterToLimelight(Limelight initialLimelightSystem, SwerveDriveSystem initialSwerveDriveSystem) {
        limelight = initialLimelightSystem;
        swerveDriveSystem = initialSwerveDriveSystem;
    }

    @Override
    public void initialize() {
        limelight.setLimelightLampOn();
        limelight.setLimelightPipeToAprilTag();
        startTime = Timer.getFPGATimestamp();
        NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("ledMode").setNumber(3);
    }

    @Override
    public void execute() {
        double tx = NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("tx").getDouble(0);
        heading_error = -tx;
        if (Math.abs(heading_error) > heading_setpoint) {
            swerveDriveSystem.moveVariable(Kp * heading_error + min_command, 0, 0, 0, Wheel.SpeedSetting.NORMAL);
        }
    }

    @Override
    public boolean isFinished() { // specifies end conditions
        if (limelight.doesLimelightHaveTarget()) {
            if (Math.abs(heading_error) < heading_setpoint) {
                swerveDriveSystem.stop();
                if (System.currentTimeMillis() - strafeTime > 1000) {
                    return true;
                };
            } else {
                strafeTime = -1;
                return false;
            }
            return false;

        }

        return false;
    }
}
