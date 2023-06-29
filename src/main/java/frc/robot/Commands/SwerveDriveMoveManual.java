package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel.SpeedSetting;

public class SwerveDriveMoveManual extends CommandBase {

    private final SwerveDriveSystem swerveDriveSystem;
    private double distance = 0;
    private double initialEncoderValue = 0;
    private double theta_radians;
    public SwerveDriveMoveManual(SwerveDriveSystem initialSwerveDriveSystem, double initialDistance, double theta_radians) {
        super();
        swerveDriveSystem = initialSwerveDriveSystem;
        this.theta_radians = theta_radians;
        distance = initialDistance;
        addRequirements(swerveDriveSystem);
    }

    @Override
    public void initialize() {
        initialEncoderValue = swerveDriveSystem.getEncoderFL();
    }

    @Override
    public void execute() {
        swerveDriveSystem.moveManual(1, 0, 0, theta_radians, SpeedSetting.NORMAL);
        //swerveDriveSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(swerveDriveSystem.getEncoderFL() - initialEncoderValue) > (distance * Constants.RELATIVE_ENC_TO_FT);
    }
    
}