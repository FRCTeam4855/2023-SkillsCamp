package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BasicMovements extends SubsystemBase implements GenericDriveSystem {

    SwerveDriveSystem driveValues = new SwerveDriveSystem();

    double y1 = 0;
    double x1 = 0;
    double x2 = 0;

    @Override
    public void moveForward() {
    }

    @Override
    public void moveBackward() {
    }

    @Override
    public void moveLeft() {
    }

    @Override
    public void moveRight() {
    }

    @Override
    public void spinClockwise() {
    }

    @Override
    public void spinCounterclockwise() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void stay() {
    }

}
