package frc.robot.Commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;

public class RotateToTarget extends CommandBase {

    double Kp = -0.05;
    double min_command = 0.05;
    double heading_setpoint = 3;
    double initTime;
    double heading_error;
    private Limelight limelight;
    private SwerveDriveSystem swerveDriveSystem;

    public RotateToTarget(Limelight initialLimelightSystem, SwerveDriveSystem initialSwerveDriveSystem) {
        limelight = initialLimelightSystem;
        swerveDriveSystem = initialSwerveDriveSystem;
    }

    @Override
    public void initialize() {
        limelight.setLimelightLampOn();
        limelight.setLimelightPipeToRetroTape();
        NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("ledMode").setNumber(3);
    }

    @Override
    public void execute() {
        double tx = NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("tx").getDouble(0);
        heading_error = tx;
        if (Math.abs(heading_error) > heading_setpoint) {
            swerveDriveSystem.spinCounterclockwise();
        } else {
            swerveDriveSystem.spinClockwise();
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