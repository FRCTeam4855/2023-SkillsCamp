package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Intake;

public class IntakeStop extends CommandBase {

    private Intake intake;

    public IntakeStop(Intake initalIntake) {
        intake = initalIntake;  
    }

    @Override
    public void initialize() {
    intake.setIntakeStop();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
