package org.firstinspires.ftc.teamcode.Mechanisms;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrive {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private IMU imu;

    public void init(HardwareMap hwMap){
        frontLeft = hwMap.get(DcMotor.class, "fl");
        frontRight = hwMap.get(DcMotor.class, "fr");
        backLeft = hwMap.get(DcMotor.class, "bl");
        backRight = hwMap.get(DcMotor.class, "br");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double strafe, double rotate){
       double frontLeftPower = forward - strafe + rotate;
        double backLeftPower = forward + strafe + rotate;
        double frontRightPower = forward + strafe - rotate;
        double backRightPower = forward - strafe - rotate;

       double maxPower = 1;


        double flPower = Math.min(maxPower, frontLeftPower);
        double frPower = Math.min(maxPower,frontRightPower);
       double blPower = Math.min(maxPower,backLeftPower);
       double brPower = Math.min(maxPower, backRightPower);




        frontLeft.setPower(flPower);
        frontRight.setPower(frPower);
        backLeft.setPower(blPower);
        backRight.setPower(brPower);
    }

    public void driveFieldRelative(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);
        this.drive(newForward,newStrafe, rotate);
    }
}

