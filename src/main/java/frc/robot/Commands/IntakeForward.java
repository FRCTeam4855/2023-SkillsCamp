package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.IntakeMotor;

public class IntakeForward extends CommandBase {

    // member variable the saves the value from the constructor
    private IntakeMotor intakeMotor;

    // When creating a new instance of the command, make sure
    // we require someone to say which specific intake instance to use
    public IntakeForward(IntakeMotor initialIntake){
        intakeMotor = initialIntake;
    }


    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        intakeMotor.setIntakeForward();

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
