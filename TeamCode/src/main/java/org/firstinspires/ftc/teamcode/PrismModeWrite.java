package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver.LayerHeight;
import static org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver.Artboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;


import java.util.concurrent.TimeUnit;

@TeleOp(name="PrismModeWrite", group="Linear OpMode")
//@Disabled

public class PrismModeWrite extends LinearOpMode {

    GoBildaPrismDriver prism;

    @Override
    public void runOpMode() {

        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");

        telemetry.addData("Device ID: ", prism.getDeviceID());
        telemetry.addData("Firmware Version: ", prism.getFirmwareVersionString());
        telemetry.addData("Hardware Version: ", prism.getHardwareVersionString());
        telemetry.addData("Power Cycle Count: ", prism.getPowerCycleCount());
        telemetry.addData("Run Time (Minutes): ", prism.getRunTime(TimeUnit.MINUTES));
        telemetry.update();

        prism.enableDefaultBootArtboard(true);
        prism.setDefaultBootArtboard(Artboard.ARTBOARD_0);
        prism.setStripLength(29);

   /*     PrismAnimations.Solid solid = new PrismAnimations.Solid(Color.YELLOW);
        PrismAnimations.DroidScan droidScan = new PrismAnimations.DroidScan(Color.CYAN);
        PrismAnimations.Pulse pulse = new PrismAnimations.Pulse(Color.GREEN);

*/
        // Wait for the game to start (driver presses START)
        waitForStart();
        resetRuntime();
/*
        solid.setBrightness(100);
        solid.setStartIndex(0);
        solid.setStopIndex(24);

        droidScan.setBrightness(100);
        droidScan.setStartIndex(0);
        droidScan.setStopIndex(24);

        pulse.setBrightness(100);

        // Artboard 0: Slide off Driver off

        prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, solid);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_1, droidScan);
        sleep(500);
        prism.saveCurrentAnimationsToArtboard(Artboard.ARTBOARD_0);
        sleep(2000);
        prism.clearAllAnimations();
        // Artboard 1: Slide on Driver off

        prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, solid);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_1, droidScan);
        // top-right green indicates slide mode on
        pulse.setStartIndex(8);
        pulse.setStopIndex(12);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_2, pulse);
        sleep(500);
        prism.saveCurrentAnimationsToArtboard(Artboard.ARTBOARD_1);
        sleep(2000);
        prism.clearAllAnimations();

        // Artboard 2: Slide off Driver on

        prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, solid);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_1, droidScan);
        // top-left green indicates driver mode on
        pulse.setStartIndex(13);
        pulse.setStopIndex(17);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_2, pulse);
        sleep(500);
        prism.saveCurrentAnimationsToArtboard(Artboard.ARTBOARD_2);
        sleep(2000);
        prism.clearAllAnimations();

        // Artboard 3: Slide on Driver on

        prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, solid);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_1, droidScan);
        // top-right green indicates slide mode on
        pulse.setStartIndex(8);
        pulse.setStopIndex(12);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_2, pulse);
        // top-left green indicates driver mode on
        pulse.setStartIndex(13);
        pulse.setStopIndex(17);
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_3, pulse);
        sleep(500);
        prism.saveCurrentAnimationsToArtboard(Artboard.ARTBOARD_3);
        sleep(2000);
        prism.clearAllAnimations();

        sleep(3000);
        telemetry.addLine("Done");
        telemetry.update();

        prism.loadAnimationsFromArtboard(Artboard.ARTBOARD_1);
        telemetry.addLine("Artboard 1 True False");
        telemetry.update();
        sleep(1000);

        prism.loadAnimationsFromArtboard(Artboard.ARTBOARD_2);
        telemetry.addLine("Artboard 2 False True");
        telemetry.update();
        sleep(1000);

        prism.loadAnimationsFromArtboard(Artboard.ARTBOARD_3);
        telemetry.addLine("Artboard 3 True True");
        telemetry.update();
        sleep(1000);*/
    }
}
