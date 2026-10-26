package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooting;
import org.firstinspires.ftc.teamcode.Mechanisms.turret;

@TeleOp
public class FieldOriented extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    double forward, strafe, rotate;
    Intake intake = new Intake();//Intake (capital I) is the class lowercase intake is the name when referring to the instance created for this class
    Shooting shooting = new Shooting();
    //manualTurret manualTurret = new manualTurret();
    turret turret = new turret();

    double intakePower, midtakePower, sidetakePower, gateAngle, flywheelPower, turretPower;
    public AnalogInput floodgate;
    Limelight3A limelight;
    private IMU imu;


    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooting.init(hardwareMap);
        turret.init(hardwareMap);
        //manualTurret.init(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        //april tag pipeline changes in the limelight setup
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        floodgate = hardwareMap.get(AnalogInput.class, "floodgate");
        telemetry.addLine("initialization complete");

    }

    @Override
    public void loop() {
        flywheelPower = 1;
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        //turretPower = gamepad2.right_stick_x;

        if (gamepad2.leftBumperWasPressed()){
            //this is going to be the intake mode so intake midtake and sidtake running
            //GATE CLOSED
            intakePower = 1;
            midtakePower = 1;
            sidetakePower = 1;
            gateAngle = 90;
        }

        if (gamepad2.rightBumperWasPressed()){
            // shooting mode: midtake and flywheel on and gate open
            midtakePower = 1;
            gateAngle = 0;
        }

        if(gamepad2.aWasPressed()){
            gateAngle = 0;
            intakePower = -1;
            midtakePower = -1;
        }

        LLResult result = limelight.getLatestResult();
        turret.update(result);

        // Floodgate Power Switch Amperage Measure
        double voltage = floodgate.getVoltage();
        String truncated_voltage = String.format("%.2f", voltage);
        double amperage = voltage / 3.3 * 80;
        String truncated_amperage = String.format("%.2f", amperage);

        telemetry.addData("Turret Revs", turret.getTurretRevs()); // if revs is greater or equal to _____ stop or go other direction
        telemetry.addData("Floodgate Measured Voltage Representation: ",truncated_voltage + "V");
        telemetry.update();




        drive.driveFieldRelative(forward,strafe,rotate);
        intake.intake(intakePower, midtakePower, sidetakePower); //intake in white refers to the method inside Intake
        shooting.shooting(midtakePower, flywheelPower);
        shooting.gateOpen(gateAngle);
        intake.gateClose(gateAngle);
        //manualTurret.manual(turretPower,flywheelPower);

    }
}
