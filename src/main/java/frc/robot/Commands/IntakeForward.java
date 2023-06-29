package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Intake;

public class IntakeForward extends CommandBase {

    // member variable the saves the value from the constructor
    private Intake intake;

    // When creating a new instance of the command, make sure
    // we require someone to say which specific intake instance to use
    public IntakeForward(Intake initialIntake){
        intake = initialIntake;
    }


    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        intake.setIntakeForward();

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
