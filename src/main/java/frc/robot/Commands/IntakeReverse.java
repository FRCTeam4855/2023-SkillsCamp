package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Intake;

public class IntakeReverse extends CommandBase {

    private Intake intake;

    public IntakeReverse(Intake initialIntake) {
        intake = initialIntake;
    }

    @Override
    public void initialize() {
    intake.setIntakeBackward();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
