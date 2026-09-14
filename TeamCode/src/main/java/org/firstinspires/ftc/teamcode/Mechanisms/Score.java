package org.firstinspires.ftc.teamcode.Mechanisms;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class Score {


    private DcMotorEx outtake;

    public void init(HardwareMap hwMap) {
        outtake = hwMap.get(DcMotorEx.class, "W2");
        outtake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtake.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public void outtakeScore(double outtakeVelocity) {
        outtake.setVelocity(outtakeVelocity);

    }

    public double getOuttakeVelocity() {
        return outtake.getVelocity();
    }

    public void autoOuttake(double outtakePower, long millis) throws InterruptedException {
        outtake.setPower(outtakePower);
        sleep(millis);
    }

}


