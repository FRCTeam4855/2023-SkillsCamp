package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Limelight;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Limelight.doesLimelightHaveTarget;

public class FindRetroTape extends CommandBase{

    private final SwerveDriveSystem swerveDriveSystem;
    
    public FindRetroTape(SwerveDriveSystem initialSwerveDriveSystem){
        swerveDriveSystem = initialSwerveDriveSystem;
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = table.getEntry("ty");
        // boolean doesLimelightHaveTarget = Limelight.doesLimelightHaveTarget;
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        swerveDriveSystem.spinClockwise();
    }
    @Override 
    public boolean isFinished(){
        if (NetworkTableInstance.getDefault().getTable("limelight-rams").getEntry("tv").getDouble(0) == 1){
            return true;
          } else {
            return false;
          }
    }
}
