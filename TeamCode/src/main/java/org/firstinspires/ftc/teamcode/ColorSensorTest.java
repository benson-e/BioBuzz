package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSensor;
import org.firstinspires.ftc.teamcode.Mechanisms.AutoScore;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.Mechanisms.Score;
import org.firstinspires.ftc.teamcode.Mechanisms.intake;

@TeleOp
public class ColorSensorTest extends OpMode {
    ColorSensor colorSensor = new ColorSensor();
    AutoScore score1 = new AutoScore();
    MecanumDrive drive = new MecanumDrive();
    intake intakeHold = new intake(); //intake
    Score outtakeScore = new Score(); //score
    ColorSensor.DetectedColor detectedColor;
    @Override
    public void init() {
        colorSensor.init(hardwareMap);
        score1.init(hardwareMap);
        drive.init(hardwareMap); //drive
        intakeHold.init(hardwareMap);//intake
        outtakeScore.init(hardwareMap);//outtake
    }

    @Override
    public void loop() {
    detectedColor = colorSensor.getDetectedColor(telemetry);
    telemetry.addData("Color Detected", detectedColor);
    if (gamepad1.a) {
        telemetry.addData("button","s");
        try {
            score1.AutonScore(1,1,1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    }

}
