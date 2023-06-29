package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.PrettyLights;

public class LightsOnCommand extends CommandBase{
    
    private PrettyLights prettyLights;
    private double newColor;

    public LightsOnCommand(PrettyLights prettyLights, double newColor){
        this.prettyLights = prettyLights;
        this.newColor = newColor;
        addRequirements(prettyLights);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
       prettyLights.setLEDs(newColor);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
