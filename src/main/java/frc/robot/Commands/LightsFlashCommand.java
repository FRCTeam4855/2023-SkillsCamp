package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.PrettyLights;

public class LightsFlashCommand extends CommandBase {

    private PrettyLights prettyLights;
    private double newColor;
    private double oldColor;
    private double startTime;
    private double seconds;

    public LightsFlashCommand(PrettyLights prettyLights, double newColor, double seconds) {
        this.prettyLights = prettyLights;
        this.newColor = newColor;
        this.seconds = seconds;
        addRequirements(prettyLights);
    }

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
        oldColor = prettyLights.getLEDs();

    }

    @Override
    public void execute() {
        prettyLights.setLEDs(newColor);
    }

    @Override
    public boolean isFinished() {
        if (Timer.getFPGATimestamp() - startTime > seconds) {
            prettyLights.setLEDs(oldColor);

            return true;
        } else {
            return false;
        }
    }
}
