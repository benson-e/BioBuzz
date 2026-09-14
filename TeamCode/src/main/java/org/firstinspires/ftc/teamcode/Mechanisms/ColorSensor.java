package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensor {
    NormalizedColorSensor colorSensor;
    //purple and green

    public enum DetectedColor{
        SOMETHING,
        UNKNOWN,
    }

    public void init(HardwareMap hwMap){
        colorSensor = hwMap.get(NormalizedColorSensor.class, "colour");
        colorSensor.setGain(8);
    }

    public DetectedColor getDetectedColor(Telemetry telemetry){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();//return 4 values Red blue green and alpha:brightness

        float normRed, normGreen, normBlue;
        normRed = colors.red/ colors.alpha;
        normGreen = colors.green/ colors.alpha;
        normBlue = colors.blue/ colors.alpha;

        telemetry.addData("red", normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);

        //ADD IF STATEMENTS FOR SPECIFIC COLORS ADDED
        /*
        something = <0.03, <0.0100, <0.08
        */
        if (normRed > 0.03 && normGreen > 0.0100 && normBlue > 0.08){
           return DetectedColor.UNKNOWN;
        } else {
            return DetectedColor.SOMETHING;
        }
    }
}
