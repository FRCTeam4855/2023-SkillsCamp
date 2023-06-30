package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Subsystems.PneumaticBase.*;

public class IntakeArm extends SubsystemBase {

    DoubleSolenoid IntakeArm = new DoubleSolenoid(REV_PH_MODULE, PneumaticsModuleType.REVPH, 0, 1);

    public void armUp() {
        IntakeArm.set(Value.kForward);
    }

    public void armDown() {
        IntakeArm.set(Value.kReverse);
    }

    public void armToggle() {
        IntakeArm.toggle();
    }

    public boolean isArmUp() {
        return IntakeArm.get() == Value.kForward;
    }

    public boolean isArmDown() {
        return IntakeArm.get() == Value.kReverse;
    }

}