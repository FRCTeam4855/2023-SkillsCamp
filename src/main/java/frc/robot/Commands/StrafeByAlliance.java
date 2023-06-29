package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems.SwerveDriveSystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class StrafeByAlliance extends CommandBase {
    private final SwerveDriveSystem swerveDriveSystem;
    private double distance = 0;
    private double initialEncoderValue = 0;
    public StrafeByAlliance(SwerveDriveSystem initialSwerveDriveSystem, double initialDistance) {
        super();
        swerveDriveSystem = initialSwerveDriveSystem;
        distance = initialDistance;
        addRequirements(swerveDriveSystem);
    }

    @Override
    public void initialize() {
        initialEncoderValue = swerveDriveSystem.getEncoderFL();
    }

    @Override
    public void execute() {
        if (DriverStation.getAlliance() == Alliance.Red)
        swerveDriveSystem.moveLeft();

        if (DriverStation.getAlliance() == Alliance.Blue)
        swerveDriveSystem.moveRight();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(swerveDriveSystem.getEncoderFL() - initialEncoderValue) > (distance * Constants.RELATIVE_ENC_TO_FT);
    }
    
}