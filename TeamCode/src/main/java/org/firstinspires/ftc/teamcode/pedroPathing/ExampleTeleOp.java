package org.firstinspires.ftc.teamcode.pedroPathing;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
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
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.function.Supplier;

@Configurable
@TeleOp
public class ExampleTeleOp extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> pathChain;
    private TelemetryManager telemetryM;
    private boolean slowMode = false;
    private double slowModeMultiplier = 0.5;
    private Limelight3A limelight;
   private IMU imu;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        //april tag pipeline changes in the limelight setup
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(getRobotPoseFromCamera(), new Pose(53, 91))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(-45), 0.8))
                .build();
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
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
            return new Pose(0, 0, 0, FTCCoordinates.INSTANCE).getAsCoordinateSystem(PedroCoordinates.INSTANCE);
        }


    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();

        if (!automatedDrive) {
            //Make the last parameter false for field-centric
            //In case the drivers want to use a "slowMode" you can scale the vectors

            //This is the normal version to use in the TeleOp
            if (!slowMode) follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    false // Robot Centric
            );

                //This is how it looks with slowMode on
            else follower.setTeleOpDrive(
                    -gamepad1.left_stick_y * slowModeMultiplier,
                    -gamepad1.left_stick_x * slowModeMultiplier,
                    -gamepad1.right_stick_x * slowModeMultiplier,
                    false // Robot Centric
            );
        }

        //Automated PathFollowing
        if (gamepad1.aWasPressed()) {
            follower.followPath(pathChain.get());
            automatedDrive = true;
        }

        //Stop automated following if the follower is done
        if (automatedDrive && (gamepad1.bWasPressed() || !follower.isBusy())) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }

        //Slow Mode
        if (gamepad1.rightBumperWasPressed()) {
            slowMode = !slowMode;
        }

        //Optional way to change slow mode strength
        if (gamepad1.xWasPressed()) {
            slowModeMultiplier += 0.25;
        }

        //Optional way to change slow mode strength
        if (gamepad2.yWasPressed()) {
            slowModeMultiplier -= 0.25;
        }

        telemetryM.debug("position", follower.getPose());
        telemetryM.debug("velocity", follower.getVelocity());
        telemetryM.debug("automatedDrive", automatedDrive);
    }
}