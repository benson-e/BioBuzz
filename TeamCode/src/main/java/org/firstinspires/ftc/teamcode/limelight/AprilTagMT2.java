package org.firstinspires.ftc.teamcode.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
public class AprilTagMT2 extends OpMode {
    private Limelight3A limelight3A;
    private IMU imu;

    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight3A.pipelineSwitch(1); //double check this
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start(){
        limelight3A.start();
    }

    @Override
    public void loop() {
        // First, tell Limelight which way your robot is facing
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight3A.updateRobotOrientation(orientation.getYaw());
        LLResult result = limelight3A.getLatestResult();
        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
                double x = botpose_mt2.getPosition().x;
                double y = botpose_mt2.getPosition().y;
                telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
                telemetry.addData("botpose", botpose_mt2.toString());
            }

    }
}
