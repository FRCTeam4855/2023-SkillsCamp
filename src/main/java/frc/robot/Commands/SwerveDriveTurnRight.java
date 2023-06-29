package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;
import frc.robot.Subsystems.SwerveDriveSystem;

public class SwerveDriveTurnRight extends CommandBase {
           
    private final SwerveDriveSystem swerveDriveSystem;
    private double distance = 0;
    private double initialEncoderValue = 0;
    public SwerveDriveTurnRight(SwerveDriveSystem initialSwerveDriveSystem, double degrees) {
        super();
        swerveDriveSystem = initialSwerveDriveSystem;
        distance =(degrees * ((Math.PI * DIST_BETWEEN_WHEELS) / 360)) / 12;
        addRequirements(swerveDriveSystem);
    }

    @Override
    public void initialize() {
        initialEncoderValue = swerveDriveSystem.getEncoderFL();
    }

    @Override
    public void execute() {
        swerveDriveSystem.spinClockwise();
        //swerveDriveSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(swerveDriveSystem.getEncoderFL() - initialEncoderValue) > (distance * RELATIVE_ENC_TO_FT);
    }
    
}