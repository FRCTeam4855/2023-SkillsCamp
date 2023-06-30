package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeMotor {

    CANSparkMax intakeMotor = new CANSparkMax(9, MotorType.kBrushless);

    public void setIntakeForward() {
        intakeMotor.set(.5);
    }

    public void setIntakeBackward() {
        intakeMotor.set(-.5);
    }

    public void setIntakeStop() {
        intakeMotor.set(0);
    }
}
