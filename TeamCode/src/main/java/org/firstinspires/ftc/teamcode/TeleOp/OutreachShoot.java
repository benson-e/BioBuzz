package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.Mechanisms.Midtake;
import org.firstinspires.ftc.teamcode.Mechanisms.Score;
import org.firstinspires.ftc.teamcode.Mechanisms.intake;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;

@TeleOp
public class OutreachShoot extends OpMode {
    double intakePower, outtakePower, midPower;
    double outtakeSpeed;
    public AnalogInput floodgate;

    GoBildaPrismDriver prism;
    intake intakeHold = new intake(); //intake
    Score outtakeScore = new Score(); //score
    Midtake midtake = new Midtake();

    @Override
    public void init() {
        intakeHold.init(hardwareMap);//intake
        outtakeScore.init(hardwareMap);//outtake
        midtake.init(hardwareMap);
        floodgate = hardwareMap.get(AnalogInput.class, "floodgate");
        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");

    }

    @Override
    public void loop() {
        outtakeSpeed = 1840;
        prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_3); // purple artboard 3 status: slide on driver on
        outtakePower = -outtakeSpeed;
        intakePower = -1;
        midPower = 1;

        // Floodgate Power Switch Amperage Measure
        double voltage = floodgate.getVoltage();
        String truncated_voltage = String.format("%.2f", voltage);
        double amperage = voltage / 3.3 * 80;
        String truncated_amperage = String.format("%.2f", amperage);

        intakeHold.intakeHold(intakePower);
        midtake.midtakeHold(midPower);
        outtakeScore.outtakeScore(outtakePower);
        telemetry.addData("Launcher Velocity", outtakeScore.getOuttakeVelocity());
        //telemetry.addData("Floodgate Measured Voltage Representation: ",truncated_voltage + "V");
        telemetry.addData("Total Current Draw: ",truncated_amperage + "A");
        telemetry.update();
    }
}