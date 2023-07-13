/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.PrettyLights;
import frc.robot.Subsystems.SwerveDriveSystem;
import frc.robot.Subsystems.Wheel;
import frc.robot.Subsystems.IntakeArm;
import frc.robot.Subsystems.IntakeMotor;
import frc.robot.Subsystems.Limelight;
import frc.robot.Commands.BalanceLR;
import frc.robot.Commands.CenterToLimelight;
import frc.robot.Commands.LightsOnCommand;
import frc.robot.Commands.Stay;
import frc.robot.Commands.StrafeByAlliance;
import frc.robot.Commands.DriveUntilBalanced;
import frc.robot.Commands.FindRetroTape;
import frc.robot.Commands.IntakeArmDown;
import frc.robot.Commands.IntakeArmToggle;
import frc.robot.Commands.IntakeArmUp;
import frc.robot.Commands.IntakeForward;
import frc.robot.Commands.IntakeReverse;
import frc.robot.Commands.IntakeStop;
import frc.robot.Commands.LightsFlashCommand;
import frc.robot.Commands.SwerveDriveMoveBackward;
import frc.robot.Commands.SwerveDriveMoveForward;
import frc.robot.Commands.SwerveDriveMoveLeft;
import frc.robot.Commands.SwerveDriveMoveManual;
import frc.robot.Commands.SwerveDriveMoveRight;
import frc.robot.Commands.SwerveDriveStop;
import frc.robot.Commands.SwerveDriveTurnLeft;
import frc.robot.Commands.SwerveDriveTurnRight;
import frc.robot.Constants.ArmSetpoint;
import frc.robot.Commands.FindRetroTape;
import frc.robot.Commands.CenterToLimelight;
public class Robot extends TimedRobot {

  private boolean fieldOriented; // robot is in field oriented or robot oriented
  private double theta_radians; // gyro angle offset field oriented zero vs robot zero (front) for swerve calcs
  private Wheel.SpeedSetting driveSpeed = Wheel.SpeedSetting.NORMAL;

  private static final String kAuton1 = "1. Drive Forward";
  private static final String kAuton2 = "2. Back, Drop, Forward";
  private static final String kAuton3 = "3. B, D, F, B, Balance";
  private static final String kAuton4 = "Unused";
  private static final String kAuton5 = "ZZZ KKEP UNUSED";
  private static final String kAuton6 = "balance test";

  private String m_autoSelected; // This selects between the two autonomous
  public SendableChooser<String> m_chooser = new SendableChooser<>(); // creates the ability to switch between autons on
                                                                      // SmartDashboard
  private XboxController xboxDriver = new XboxController(0);
  private XboxController xboxOperator = new XboxController(1);
  AHRS gyro = new AHRS(); // defines the gyro
  private SwerveDriveSystem driveSystem = new SwerveDriveSystem();
  private Limelight limelight = new Limelight();
  private PrettyLights prettyLights1 = new PrettyLights();
  private IntakeMotor frontIntakeMotor = new IntakeMotor();
  private IntakeArm frontIntakeArm = new IntakeArm();
  ArmSetpoint currentSetpoint;

  // command related declarations
  // Command moveForward = new SwerveDriveMoveForward(driveSystem, 10);
  // Command initLights = new LightsOnCommand(prettyLights1,
  // PrettyLights.BPM_PARTYPALETTE);
  // Command autonLights = new LightsOnCommand(prettyLights1,
  // PrettyLights.RAINBOW_GLITTER);
  // Command teleopLights = new LightsOnCommand(prettyLights1,
  // PrettyLights.BLUE_GREEN);
  // Command unbalancedLights = new LightsOnCommand(prettyLights1,
  // PrettyLights.COLORWAVES_LAVAPALETTE);
  // Command moveArmToOne = new MoveArmToSetpoint(armExtend, armPivot,
  // ArmSetpoint.One);
  // Command moveArmToTwo = new MoveArmToSetpoint(armExtend, armPivot,
  // ArmSetpoint.Two);
  // Command moveArmToThree = new MoveArmToSetpoint(armExtend, armPivot,
  // ArmSetpoint.Three);
  // Command moveArmToFour = new MoveArmToSetpoint(armExtend, armPivot,
  // ArmSetpoint.Four);
  // Command openPaws = new OpenPaws(intakePaws);
  // Command closePaws = new ClosePaws(intakePaws);

  // *************************
  // ********robotInit********
  // *************************
  // This function is run when the robot is first started up and should be
  // used for any initialization code.

  @Override
  public void robotInit() {
    fieldOriented = true;
    m_chooser.addOption("1. pick up cone inside robot and drive out of comm", kAuton1);
    m_chooser.setDefaultOption("2. Drop cone on mid and drive out of comm", kAuton2);
    m_chooser.addOption("3. Drop cone on mid, drive and balance on charge station", kAuton3);
    m_chooser.addOption("4. WIP DO NOT USE", kAuton4);
    m_chooser.addOption("5. ZZZ KEEP UNUSED", kAuton5);
    m_chooser.addOption("6. balance test", kAuton6);
    prettyLights1.setLEDs(.01);

    SmartDashboard.putData(m_chooser); // displays the auton options in shuffleboard, put in init block
    CameraServer.startAutomaticCapture(); // starts the usb cameras
    CameraServer.startAutomaticCapture();

  }

  // *************************
  // ******robotPeriodic******
  // *************************
  // This function is called every robot packet, no matter the mode. Use
  // this for items like diagnostics that you want ran during disabled,
  // autonomous, teleoperated and test.This runs after the mode specific
  // periodic functions, but before LiveWindow and SmartDashboard
  // integrated updating.

  @Override
  public void robotPeriodic() {
    limelight.updateDashboard(); // runs block in limeight subsystem for periodic update

    // Driving Subsystem Dashboard
    // SmartDashboard.putNumber("Encoder FL",
    // driveSystem.wheelFL.getAbsoluteValue()); // Front Left Wheel Encoder Values
    // SmartDashboard.putNumber("Encoder BL",
    // driveSystem.wheelBL.getAbsoluteValue()); // Back Left Wheel Encoder Values
    // SmartDashboard.putNumber("Encoder BR",
    // driveSystem.wheelBR.getAbsoluteValue()); // Back Right Wheel Encoder Values
    // SmartDashboard.putNumber("Encoder FR",
    // driveSystem.wheelFR.getAbsoluteValue()); // Front Right Wheel Encoder Values
    SmartDashboard.putNumber("DriveEncoder FL", driveSystem.getEncoderFL());
    SmartDashboard.putNumber("Encoder FL FT", driveSystem.getRelativeEncoderFT());
    // Misc and Sensor Dashboard
    SmartDashboard.putBoolean("Field Oriented", fieldOriented); // shows true/false for driver oriented
    SmartDashboard.putNumber("Gyro Get Yaw", gyro.getYaw()); // pulls yaw value
    SmartDashboard.putNumber("Gyro Get Pitch", gyro.getPitch()); // pulls Pitch value
    SmartDashboard.putBoolean("Is Arm Down", frontIntakeArm.isArmDown());
    // SmartDashboard.putBoolean("Balancing", Balancing.isBalancing); // shows true
    // if robot is attempting to balance

    CommandScheduler.getInstance().run(); // must be called from the robotPeriodic() method Robot class or the scheduler
                                          // will never run, and the command framework will not work

  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  // *************************
  // *****autonomousInit******
  // *************************

  @Override
  public void autonomousInit() {
    // armPivot.resetPivotEncoderZero();
    // armExtend.resetExtendEncoderVariable(95);
    driveSystem.resetRelativeEncoders();
    gyro.reset();
    fieldOriented = true;
    // gyro.setAngleAdjustment(180);
    m_autoSelected = m_chooser.getSelected(); // pulls auton option selected from shuffleboard
    SmartDashboard.putString("Current Auton:", m_autoSelected); // displays which auton is currently running

    switch (m_autoSelected) {

      case kAuton1:
        CommandScheduler.getInstance().schedule(
            new LightsOnCommand(prettyLights1, PrettyLights.RAINBOW_GLITTER)
                // .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.One,
                // currentSetpoint))
                // .andThen(new ClosePaws(intakePaws))
                // .andThen(new SwerveDriveMoveBackward(driveSystem, 20))
                // .andThen(new SwerveDriveStop(driveSystem))
                .andThen(new Stay(driveSystem)));
      default:
        break;

      case kAuton2:
        CommandScheduler.getInstance().schedule(
            new LightsOnCommand(prettyLights1, PrettyLights.RAINBOW_GLITTER)

                .andThen(new WaitCommand(1))// manual delays for cone to balance in intake
                // dropping cone
                // .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.Five,
                // currentSetpoint))
                // .andThen(new WaitCommand(1))
                // .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.One,
                // currentSetpoint))
                // .andThen(new WaitCommand(.5))
                // moving out of community
                .andThen(new StrafeByAlliance(driveSystem, .75))
                .andThen(new SwerveDriveMoveBackward(driveSystem, 9.5))
                // .andThen(new SwerveDriveMoveRight(driveSystem, 7))
                // .andThen(new SwerveDriveMoveBackward(driveSystem, 8))
                .andThen(new SwerveDriveStop(driveSystem)));

        break;

      case kAuton3:

        CommandScheduler.getInstance().schedule(
            new LightsOnCommand(prettyLights1, PrettyLights.RAINBOW_GLITTER)
                // .andThen(new WaitCommand(.5))// manual delays for cone to balance in intake
                // .andThen(new ClosePaws(intakePaws))
                // .andThen(new WaitCommand(.5))
                // moving to charge station
                .andThen(new SwerveDriveMoveBackward(driveSystem, 1.25))
                // .andThen(new SwerveDriveTurnLeft(driveSystem, 15))
                // .andThen(new SwerveDriveMoveManual(driveSystem, 8, theta_radians))
                .andThen(new SwerveDriveTurnRight(driveSystem, 90))
                // .andThen(new Balancing(driveSystem, gyro))
                .andThen(new SwerveDriveMoveRight(driveSystem, 8.85))// but super not 8.8
                .andThen(new SwerveDriveStop(driveSystem)));

        // save this, auton 3:
        // CommandScheduler.getInstance().schedule(
        // new LightsOnCommand(prettyLights1, PrettyLights.RAINBOW_GLITTER)
        // .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.One,
        // currentSetpoint))
        // .andThen(new ClosePaws(intakePaws))
        // // dropping cone
        // .andThen(new SwerveDriveMoveBackward(driveSystem, ATON_DIST_TWO))
        // .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.Four,
        // currentSetpoint))
        // .andThen(new OpenPaws(intakePaws))
        // // moving out of community
        // .andThen(new SwerveDriveMoveForward(driveSystem, ATON_DIST_ONE))
        // // moving onto platform
        // .andThen(new SwerveDriveMoveBackward(driveSystem, ATON_DIST_TWO))
        // // // or, depending on where we are...
        // // .andThen( new SwerveDriveMoveLeft(driveSystem, 20))
        // // .andThen( new SwerveDriveMoveBackward(driveSystem, ATON_DIST_TWO))
        // // // or we could have
        // // .andThen( new SwerveDriveMoveRight(driveSystem, 20))
        // // .andThen( new SwerveDriveMoveBackward(driveSystem, ATON_DIST_TWO))
        // // // end them all with balancing
        // .andThen(new Balancing(driveSystem, gyro))
        // .andThen(new SwerveDriveStop(driveSystem)));
        // break;

      case kAuton4:
        CommandScheduler.getInstance().schedule(
            new LightsOnCommand(prettyLights1, PrettyLights.RAINBOW_GLITTER)
                // moving to charge station
                .andThen(new SwerveDriveMoveBackward(driveSystem, 1.25))
                .andThen(new SwerveDriveTurnLeft(driveSystem, 15))
                .andThen(new SwerveDriveMoveManual(driveSystem, 8, theta_radians))
                .andThen(new SwerveDriveTurnRight(driveSystem, 90))
                // .andThen(new Balancing(driveSystem, gyro))
                .andThen(new SwerveDriveMoveRight(driveSystem, 8.75))
                .andThen(new DriveUntilBalanced(driveSystem, gyro))
                .andThen(new SwerveDriveStop(driveSystem)));

      case kAuton5:
        CommandScheduler.getInstance().schedule((new WaitCommand(.5)));
        break;

      case kAuton6:
        CommandScheduler.getInstance().schedule(

        );
    }
  }

  // *************************
  // ***autonomousPeriodic****
  // *************************

  @Override
  public void autonomousPeriodic() {
  }

  // *************************
  // ********teleopInit*******
  // *************************

  @Override
  public void teleopInit() {
    // armExtend.setExtendSetpoint(null);
    // armPivot.setPivotSetpoint(null);
    driveSystem.resetRelativeEncoders();
    // gyro.reset();
    // gyro.zeroYaw();
    prettyLights1.setLEDs(PrettyLights.CONFETTI);
    fieldOriented = true;
    frontIntakeArm.armDown(); 
    driveSystem.stop();
  }

  // *************************
  // *****teleopPeriodic******
  // *************************
  // This function is called periodically during operator control.

  @Override
  public void teleopPeriodic() {

    double x1 = xboxDriver.getRawAxis(0) + xboxDriver.getRawAxis(2) - xboxDriver.getRawAxis(3); // set left and right
                                                                                                // drive movements to
                                                                                                // drive controller
                                                                                                // left x-axis
    double x2 = xboxDriver.getRawAxis(4); // set rotate drive movements to drive controller right x-axis
    double y1 = xboxDriver.getRawAxis(1); // set forwards and backwards drive movements to drive controller left y-axis

    // CommandScheduler.getInstance().schedule(
    // teleopLights);

    // *************************
    // *****Driver Controls*****
    // *************************

    // additional strafing commands

    // this calculation is used for swerve depending on fieldOriented or
    // robotOriented
    if (fieldOriented) {
      theta_radians = gyro.getYaw() * Math.PI / 180; // FieldOriented (whatever encoder 0 value is = forward)
    } else
      theta_radians = 0; // RobotOriented (robot front is always forward)

    // Drive the robot
    if ((!xboxDriver.getRawButton(DRV_SPD_TURBO_RB)) && (!xboxDriver.getRawButton(DRV_SPD_PRECISE_LB))) {
      driveSpeed = Wheel.SpeedSetting.NORMAL;
    } // sets speed back to normal every 20ms
    if (xboxDriver.getRawButton(DRV_SPD_TURBO_RB)) {
      driveSpeed = Wheel.SpeedSetting.TURBO;
    }
    if (xboxDriver.getRawButton(DRV_SPD_PRECISE_LB)) {
      driveSpeed = Wheel.SpeedSetting.PRECISE;
    }

    if (xboxDriver.getAButton()) {
      driveSystem.setStay();
    } else {
      driveSystem.moveVariable(x1, y1, x2, theta_radians, driveSpeed);
    }

    // Reset the relative encoders if you press B button
    if (xboxDriver.getRawButtonPressed(ENCODER_RESET_B)) {
      driveSystem.resetRelativeEncoders();
    }

    // zeros the gyro if you press the Y button
    if (xboxDriver.getRawButtonPressed(GYRO_RESET_Y)) {
      gyro.reset();
      gyro.setAngleAdjustment(0);
    }

    // This toggles field oriented on or off when x is pressed
    if (xboxDriver.getRawButtonPressed(ORIENTATION_TOGGLE_X)) {
      fieldOriented = !fieldOriented;
    }

    // boolean isBalancing;
    // if (xboxDriver.getRawButton(DRIVER_BALANCE_BCK) && Math.abs(gyro.getPitch())
    // > 2) {
    // isBalancing = true;
    // driveSystem.moveManual(0, (gyro.getPitch() / -40), 0, theta_radians,
    // driveSpeed);
    // } else {
    // isBalancing = false;
    // }
    if (xboxDriver.getRawButton(DRIVER_BALANCE_BCK)) {
      // CommandScheduler.getInstance().schedule(new Balancing(driveSystem, gyro));
      if (Math.abs(gyro.getPitch()) > 2) {
        double pitchAngleRadians = gyro.getPitch() * (Math.PI / 180.0);
        double yAxisRate = Math.sin(pitchAngleRadians) * -1.8;
        driveSystem.moveVariable(0, yAxisRate, 0, 0, Wheel.SpeedSetting.NORMAL);
      }
    }

    // if (xboxDriver.getRawButton(DRIVER_FIND_TAPE_START)) {
    //   CommandScheduler.getInstance().schedule(
    //     new FindRetroTape(limelight, driveSystem));
    // }
    if (xboxDriver.getRawButtonPressed(DRIVER_FIND_TAPE_START)) {
      CommandScheduler.getInstance().schedule(
        new FindRetroTape(driveSystem, limelight)
        .andThen(new CenterToLimelight(limelight, driveSystem)));
 }

    // if (xboxDriver.getRawButtonPressed(1)) {
    // // Command moveLeft = new SwerveDriveMoveLeft(driveSystem, 3);
    // // Command moveFwd = new SwerveDriveMoveForward(driveSystem, 3);
    // // Command moveRight = new SwerveDriveMoveRight(driveSystem, 3);
    // // Command moveBack = new SwerveDriveMoveBackward(driveSystem, 3);
    // // Command turnRight = new SwerveDriveTurnRight(driveSystem, 90);
    // // Command turnLeft = new SwerveDriveTurnLeft(driveSystem, 180);
    // // Command balance = new Balancing(driveSystem, gyro);
    // // Command setupPostMoveLights = new LightsOnCommand(prettyLights1,
    // // PrettyLights.LARSONSCAN_RED);
    // // Command middleLights = new LightsOnCommand(prettyLights1,
    // // PrettyLights.BLUE_GREEN);
    // // Command floridaMansLights = new LightsOnCommand(prettyLights1,
    // // PrettyLights.C1_AND_C2_END_TO_END_BLEND);
    // // Command lightsAtTheEnd = new LightsOnCommand(prettyLights1,
    // // PrettyLights.HEARTBEAT_BLUE);
    // CommandScheduler.getInstance().schedule(

    /*
     * auton balance
     * .andThen(new SwerveDriveMoveForward(driveSystem, 15))
     * .andThen(new SwerveDriveTurnLeft(driveSystem, 140))
     * .andThen(new SwerveDriveMoveBackward(driveSystem, 7))
     * .andThen(new MoveArmToSetpoint(armExtend, armPivot, ArmSetpoint.Five))
     * .andThen(balance));
     */
    if (xboxOperator.getRawButton(INTAKE_FORWARD_RB)) {
      CommandScheduler.getInstance().schedule(
          new IntakeForward(frontIntakeMotor));
    } else
      CommandScheduler.getInstance().schedule(
          new IntakeStop(frontIntakeMotor));

    if (xboxOperator.getRawButton(INTAKE_BACKWARD_LB)) {
      CommandScheduler.getInstance().schedule(
          new IntakeReverse(frontIntakeMotor));
    } else
      CommandScheduler.getInstance().schedule(
          new IntakeStop(frontIntakeMotor));

    if (xboxOperator.getRawButtonPressed(INTAKE_DOWN_Y)) {
      CommandScheduler.getInstance().schedule(
          new IntakeArmDown(frontIntakeArm));
    }

    if (xboxOperator.getRawButtonPressed(INTAKE_UP_X)) {
      CommandScheduler.getInstance().schedule(
          new IntakeArmUp(frontIntakeArm));
    }
    if (xboxOperator.getRawButtonPressed(INTAKE_TOGGLE_A)) {
      CommandScheduler.getInstance().schedule(
        new IntakeArmToggle(frontIntakeArm));
    }
  }



  // This function is called periodically during test mode.
  @Override
  public void testPeriodic() {

  }

}