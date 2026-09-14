package org.firstinspires.ftc.teamcode.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Thread.sleep;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutoScore {
    /** has intake and outtake info for autonomous scoring possibly also needed for auto scoring in TeleOp in the future
    **/
    private DcMotorEx outtake, intake, midtake;


    public void init(HardwareMap hwMap) {
        outtake = hwMap.get(DcMotorEx.class, "W2");
        outtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake = hwMap.get(DcMotorEx.class, "W1");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midtake = hwMap.get(DcMotorEx.class,"W3");
        midtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midtake.setDirection(DcMotorEx.Direction.REVERSE);

        //intake.setDirection(DcMotorEx.Direction.REVERSE);
        //outtake.setDirection(DcMotorEx.Direction.REVERSE);

    }
    public void AutonIntake(double intakePower, double outtakePower) {
        outtake.setVelocity(outtakePower);
        intake.setPower(intakePower);

    }
    public double getOuttakeVelocity() {
        return outtake.getVelocity();
    }
    public void AutonScore(double outtakeVelocity, double intakePower, double midtakePower) throws InterruptedException {

        midtake.setPower(-0.3*midtakePower);
        sleep(200);
        midtake.setPower(0.0);

        outtake.setVelocity(outtakeVelocity);


        int count = 0;
        while (count < 301) {
            if (getOuttakeVelocity() > outtakeVelocity-40 && getOuttakeVelocity() < outtakeVelocity+40) {
                midtake.setPower(0.5 * midtakePower);
                intake.setPower(intakePower);
            } else {
                intake.setPower(0);
                midtake.setPower(0);

            }

            count++;
            sleep(10);
        }


        outtake.setVelocity(0);
        intake.setPower(0);
        midtake.setPower(0);



    }


}
