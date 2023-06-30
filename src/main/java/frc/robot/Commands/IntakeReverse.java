package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.IntakeMotor;

public class IntakeReverse extends CommandBase {

    private IntakeMotor intakeMotor;

    public IntakeReverse(IntakeMotor initialIntake) {
        intakeMotor = initialIntake;
    }

    @Override
    public void initialize() {
    intakeMotor.setIntakeBackward();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
