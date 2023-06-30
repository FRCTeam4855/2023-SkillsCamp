package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.IntakeMotor;

public class IntakeStop extends CommandBase {

    private IntakeMotor intakeMotor;

    public IntakeStop(IntakeMotor initalIntake) {
        intakeMotor = initalIntake;  
    }

    @Override
    public void initialize() {
    intakeMotor.setIntakeStop();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
