package org.firstinspires.ftc.teamcode.SimpleAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous
public class simplefar extends LinearOpMode {
    DcMotor backLeft, backRight,frontLeft,frontRight,intake,outtake,midtake;




    @Override
    public void runOpMode()  {
        backLeft = hardwareMap.get(DcMotor.class,"bl");
        backRight = hardwareMap.get(DcMotor.class, "br");
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        outtake = hardwareMap.get(DcMotor.class, "W2");
        outtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake = hardwareMap.get(DcMotor.class, "W1");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midtake = hardwareMap.get(DcMotor.class, "W3");
        midtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        forward(1,200); //long forward
    }

    public void turnRight(double power, long mil){
        frontLeft.setPower(power);
        frontRight.setPower(power);

        sleep(mil);

    }
    public void strafeRight(double power, long mil){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);


        sleep(mil);

    }

    public void turnLeft(double power, long mil){
        frontLeft.setPower(power);
        frontRight.setPower(power);

        sleep(mil);

    }
    public void strafeLeft(double power, long mil){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);


        sleep(mil);
    }

    public void forward(double power, long mil){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        sleep(mil);
    }

    public void backward(double power, long mil) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        sleep(mil);
    }

    public void AutonScore(double outtakePower, double intakePower, double midtakePower, long millis) {
        intake.setPower(intakePower);
        midtake.setPower(midtakePower);
        outtake.setPower(outtakePower);
        sleep(millis);
    }
}
