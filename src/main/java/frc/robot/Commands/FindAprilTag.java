package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Limelight;
import frc.robot.Subsystems.SwerveDriveSystem;

public class FindAprilTag extends CommandBase {

    private SwerveDriveSystem swerveDriveSystem;
    private Limelight limelightSystem;

    public FindAprilTag(SwerveDriveSystem initialSwerveDriveSystem, Limelight initialLimelightsystem) {
        swerveDriveSystem = initialSwerveDriveSystem;
        limelightSystem = initialLimelightsystem;
        // NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        // NetworkTableEntry ty = table.getEntry("ty");
        // boolean doesLimelightHaveTarget = Limelight.doesLimelightHaveTarget;
    }

    @Override
    public void initialize() {
            limelightSystem.setLimelightPipeToAprilTag();
    }

    @Override
    public void execute() {
        swerveDriveSystem.spinClockwise();
    }

    @Override
    public boolean isFinished() {
        return limelightSystem.doesLimelightHaveTarget();
    }

}
