package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class PneumaticBase extends SubsystemBase {

    public static final int REV_PH_MODULE = 10; // CAN ID for REV PH

    private Compressor compressor = new Compressor(REV_PH_MODULE, PneumaticsModuleType.REVPH);


    public void setCompressorOff() {
        compressor.disable();
    }

    public void setCompressorOn() {
        compressor.enableDigital();
    }

    public boolean isCompressorOn() {
        return compressor.isEnabled();
    }

}