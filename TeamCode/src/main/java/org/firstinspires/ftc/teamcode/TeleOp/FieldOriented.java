package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
    private turret turret = new turret();
    double intakePower, midtakePower, sidetakePower, gateAngle, flywheelPower;
    Limelight3A limelight;
    private IMU imu;


    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooting.init(hardwareMap);
        turret.init(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        //april tag pipeline changes in the limelight setup
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        telemetry.addLine("initialization complete");
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad2.aWasPressed()){
            //this is going to be the intake mode so intake midtake and sidtake running
            //GATE CLOSED
            intakePower = 1;
            midtakePower = 1;
            sidetakePower = 1;
            gateAngle = 90;
        }

        if (gamepad2.bWasPressed()){
            // shooting mode: midtake and flywheel on and gate open
            midtakePower = 1;
            gateAngle = 0;
            flywheelPower = 1;
        }

        LLResult result = limelight.getLatestResult();
        turret.update(result);



        drive.driveFieldRelative(forward,strafe,rotate);
        intake.intake(intakePower, midtakePower, sidetakePower); //intake in white refers to the method inside Intake
        shooting.shooting(midtakePower, flywheelPower);
        shooting.gateOpen(gateAngle);
        intake.gateClose(gateAngle);

    }
}
