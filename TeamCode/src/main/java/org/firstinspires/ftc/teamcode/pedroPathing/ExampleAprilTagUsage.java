package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class ExampleAprilTagUsage extends OpMode {
    private Limelight3A limelight;//any camera here
    private IMU imu;
    private Follower follower;
    private boolean following = false;
    private final Pose TARGET_LOCATION = new Pose(53, 91, Math.toRadians(-45)); //Put the target location here

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(getRobotPoseFromCamera()); //set your starting pose attempt to use camera 
        limelight.pipelineSwitch(1);
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        follower.update();

        //if you're not using limelight you can follow the same steps: build an offset pose, put your heading offset, and generate a path etc

        if (!following) {
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(new BezierLine(follower.getPose(), TARGET_LOCATION))
                            .setLinearHeadingInterpolation(follower.getHeading(), TARGET_LOCATION.minus(follower.getPose()).getAsVector().getTheta())
                            .build()
            );
        }

        //This uses the aprilTag to relocalize your robot
        //You can also create a custom AprilTag fusion Localizer for the follower if you want to use this by default for all your autos
        follower.setPose(getRobotPoseFromCamera());

        if (following && !follower.isBusy()) following = false;
    }

    private Pose getRobotPoseFromCamera() {
        //Fill this out to get the robot Pose from the camera's output (apply any filters if you need to using follower.getPose()                       for fusion)
        //Pedro Pathing has built-in KalmanFilter and LowPassFilter classes you can use for this
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));
        LLResult result = limelight.getLatestResult();
        double x = 0, y=0;
        if (result != null && result.isValid()){
            Pose3D botpose = result.getBotpose();
            x = botpose.getPosition().x;
            y= botpose.getPosition().y;
        }
        final Pose pose = new Pose(x, y, 0, FTCCoordinates.INSTANCE).getAsCoordinateSystem(PedroCoordinates.INSTANCE);
        return pose;
    }
}