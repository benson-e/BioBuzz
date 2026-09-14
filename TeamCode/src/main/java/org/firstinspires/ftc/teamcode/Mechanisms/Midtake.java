package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Midtake {
    public DcMotor midtake;

    public void init(HardwareMap hwMap){
        midtake = hwMap.get(DcMotor.class, "W3");
        midtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        midtake.setDirection(DcMotorEx.Direction.REVERSE);
    }
    public void midtakeHold(double midtakePower){
        midtake.setPower(midtakePower);
    }
}
