package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private DcMotor arm;
    private double armPower;
    private int armPositionOne, armPositionTwo;



    public void init(HardwareMap map){
        arm = map.get(DcMotor.class, "W3");
        arm.setTargetPosition(44);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void armPosition(int armPos) {
        arm.setTargetPosition(armPos);
        arm.setPower(0.3);
    }
    public int armPosition() {
        return arm.getCurrentPosition();
    }

}
