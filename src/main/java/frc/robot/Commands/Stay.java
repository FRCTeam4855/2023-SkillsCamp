package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.SwerveDriveSystem;

public class Stay extends CommandBase {
    private final SwerveDriveSystem swerveDriveSystem;

    public Stay(SwerveDriveSystem driveSystem){
        swerveDriveSystem = driveSystem;
        addRequirements(swerveDriveSystem);
    }
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
swerveDriveSystem.setStay();
    }

    @Override
    public boolean isFinished() {
        return true;

    }
}