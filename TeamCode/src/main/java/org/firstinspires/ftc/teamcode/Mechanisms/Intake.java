package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    private DcMotor intake;


    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "W1");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);
    }

    private void intake(double intakePower){
        intake.setPower(intakePower);
    }
}
