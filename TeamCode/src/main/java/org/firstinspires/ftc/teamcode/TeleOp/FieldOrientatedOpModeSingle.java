package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.Mechanisms.Midtake;
import org.firstinspires.ftc.teamcode.Mechanisms.Score;
import org.firstinspires.ftc.teamcode.Mechanisms.intake;

import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;

import com.qualcomm.robotcore.hardware.AnalogInput;


@TeleOp
public class FieldOrientatedOpModeSingle extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    double forward, strafe, rotate;
    double intakePower, outtakePower, midPower;
    boolean slideMode, slidePressed, driverMode, driverPressed = false;
    
    public AnalogInput floodgate;

    intake intakeHold = new intake(); //intake
    Score outtakeScore = new Score(); //score
    Midtake midtake = new Midtake();

    GoBildaPrismDriver prism;

    @Override
    public void init() {
        drive.init(hardwareMap); //drive
        intakeHold.init(hardwareMap);//intake
        outtakeScore.init(hardwareMap);//outtake
        midtake.init(hardwareMap);
        floodgate = hardwareMap.get(AnalogInput.class, "floodgate");
        slideMode = true;
        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");
    }

    @Override
    public void loop() {
        if(driverMode) {
            forward = -gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = -gamepad1.right_stick_x;
        } else {
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = -gamepad1.right_stick_x;
        }

        //toggle for the driver mode
        if (gamepad1.dpad_down && !driverPressed) {
            if (driverMode) {
                driverMode = false;
            } else {
                driverMode = true;
            }
            driverPressed = true;
        }
        if (!gamepad1.dpad_down){
            driverPressed = false;
        }
        //toggle for the slide mode
        if (gamepad1.dpad_up && !slidePressed) {
            if (slideMode) {
                slideMode = false;
            } else {
                slideMode = true;
            }
            slidePressed = true;
        }
        if (!gamepad1.dpad_up) {
            slidePressed = false;
        }

        //  Prism Mode Status Indicators
        if (slideMode && driverMode) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_3); // purple artboard 3 status: slide on driver on
        }

        if (!slideMode && driverMode) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_2); // blue artboard 2 status: slide off driver on
        }

        if (!driverMode && slideMode) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_1); // green artboard 1 status: slide on driver off
        }

        if (!driverMode && !slideMode) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_0); // red artboard 0 status: slide off driver off
        }

        if (slideMode) {
            if (gamepad1.a) {
                intakePower = -1;
                midPower = 0.5;
            } else if (gamepad1.b) {
                intakePower = 1;
                midPower = -0.5;
            } else {
                intakePower=0;
                midPower = 0;
            }
            outtakePower = 0.1;
        } else {
            if (gamepad1.x) {
                outtakePower = -1;
            } else if (gamepad1.y) {
                outtakePower = 1;
            }  else{
                outtakePower = 0;
            }
            if (gamepad1.b){
                intakePower = 1;
                midPower = -1;
            } else if(gamepad1.a){
                intakePower = -1;
                midPower = 1;
            }else {
                intakePower=0;
                midPower = 0;
            }
        }

        // Floodgate Power Switch Amperage Measure
        double voltage = floodgate.getVoltage();
        String truncated_voltage = String.format("%.2f", voltage);
        double amperage = voltage / 3.3 * 80;
        String truncated_amperage = String.format("%.2f", amperage);


        drive.drive(forward, strafe, rotate);
        intakeHold.intakeHold(intakePower);
        midtake.midtakeHold(midPower);
        outtakeScore.outtakeScore(outtakePower);
        telemetry.addData("Slide In Launch Mode: ",slideMode); // if true intake mode
        telemetry.addData("Driver In Launch Mode: ",driverMode); // both false is launch mode ?????
        telemetry.addData("Floodgate Measured Voltage Representation: ",truncated_voltage + "V");
        telemetry.addData("Total Current Draw: ",truncated_amperage + "A");
        telemetry.update();
    }
}