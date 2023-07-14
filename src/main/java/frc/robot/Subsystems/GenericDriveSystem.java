package frc.robot.Subsystems;

public interface GenericDriveSystem {
    void moveForward();
    void moveBackward();
    void moveLeft();
    void moveRight();
    void spinClockwise();
    void spinCounterclockwise();
    void stop();
}
