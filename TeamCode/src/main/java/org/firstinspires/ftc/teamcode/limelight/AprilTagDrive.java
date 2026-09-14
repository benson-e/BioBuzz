package org.firstinspires.ftc.teamcode.limelight;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp
public class AprilTagDrive extends OpMode {
    private Limelight3A limelight;
    private IMU imu;
    private Follower follower;
    private final Pose TARGET_LOCATION = new Pose(53, 91, Math.toRadians(-45));
    private PathChain score;
    MecanumDrive drive = new MecanumDrive();
    double forward, strafe, rotate;

    public AnalogInput floodgate;



    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        follower = Constants.createFollower(hardwareMap);
        limelight.pipelineSwitch(0);
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        floodgate = hardwareMap.get(AnalogInput.class, "floodgate");
        buildPaths();
        drive.init(hardwareMap);

    }

    public void buildPaths(){

        score = follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(getRobotPoseFromCamera(), TARGET_LOCATION)))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(45), 0.8))
                .build();
    }

    @Override
    public void loop() {
        follower.update();
        telemetry.update();
        if (gamepad1.b) {
            follower.followPath(score);
            }
        else {
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

        }
        telemetry.update();
        drive.drive(forward, strafe, rotate);
    }

    @Override
    public void start(){
        limelight.start();
    }

    private Pose getRobotPoseFromCamera() {
        //Fill this out to get the robot Pose from the camera's output (apply any filters if you need to using follower.getPose()                       for fusion)
        //Pedro Pathing has built-in KalmanFilter and LowPassFilter classes you can use for this
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            Pose3D botpose = result.getBotpose();
            double x = botpose.getPosition().x;
            double y= botpose.getPosition().y;
            double yaw = botpose.getOrientation().getYaw();
            double X = 72+(y*39.37);
            double Y = 72-(x*39.37);

                telemetry.addLine("valid");
                telemetry.addData(" Pedro", "(" + X + ", " + Y + ")");
            return new Pose(X, Y, yaw, FTCCoordinates.INSTANCE).getAsCoordinateSystem(PedroCoordinates.INSTANCE);
            }else{
                telemetry.addLine("Not valid");
            return new Pose(0, 0, 0, FTCCoordinates.INSTANCE).getAsCoordinateSystem(PedroCoordinates.INSTANCE);}

    }
}
