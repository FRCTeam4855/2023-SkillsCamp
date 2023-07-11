package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.IntakeArm;

public class IntakeArmToggle extends CommandBase{
    
    private IntakeArm intakeArm;

    public IntakeArmToggle(IntakeArm initialIntake){
      intakeArm = initialIntake; {
      // Use addRequirements() here to declare subsystem dependencies.
    }
  }
  
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      intakeArm.armToggle();
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }
  }