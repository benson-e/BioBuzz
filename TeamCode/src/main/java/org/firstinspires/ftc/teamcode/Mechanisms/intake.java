package org.firstinspires.ftc.teamcode.Mechanisms;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {
    private DcMotor intake, outtake;

    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "W1");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);

    }
    public void intakeHold(double intakePower){
        intake.setPower(intakePower);

    }
    public void autoIntake(double intakePower, long millis) throws InterruptedException {
        intake.setPower(intakePower);

        sleep(millis);
    }

}