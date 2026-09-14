package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoScore;
import org.firstinspires.ftc.teamcode.Mechanisms.intake;

@Autonomous
public class ShortRed extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(88, 8.8, Math.toRadians(0)); // Start Pose currently left corner subject to change
    private final Pose scorePose = new Pose(91, 91, Math.toRadians(-145)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    //test this seems ok

    private final Pose pickupPose = new Pose(142,7.8,Math.toRadians(0)); // setup for the following pose
    private final Pose setupPose = new Pose(72,48.5,Math.toRadians(0));
    private Path scorePreload;
    private PathChain Setup, Grab, pickup, Score1, Score2, shortSet, longSet,grabPickup3, goSetup3, scorePickup3;
    long millis;
    double outtakepower = -0.1;
    double wait = 2;
    double out = 0.775;
    AutoScore autoScore = new AutoScore(); //intake and outtake to score???

    intake intake = new intake(); //intake alone
    public void buildPaths() {
        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

    /* Here is an example for Constant Interpolation
    scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        Score1 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(),scorePose.getHeading())
                .build();

        shortSet = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, startPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(),startPose.getHeading())
                .build();

        longSet = follower.pathBuilder()
                .addPath(new BezierLine(setupPose, startPose))
                .setLinearHeadingInterpolation(setupPose.getHeading(),startPose.getHeading())
                .build();


        Grab = follower.pathBuilder()
                .addPath(new BezierCurve(startPose, pickupPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), pickupPose.getHeading())
                .build();

        Score2 = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose,scorePose))
                .setLinearHeadingInterpolation(pickupPose.getHeading(), scorePose.getHeading())
                .build();



    }

    public void autonomousPathUpdate() throws InterruptedException {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (!follower.isBusy()) {
                    /* Score Preload */

                    autoScore.AutonScore(out, 1, 1);
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(Score1, true);
                    setPathState(2);
                }
                break;

            case 2:
                if (follower.isBusy()){
                    follower.followPath(shortSet,true);
                    setPathState(-1);
                }
                break;
            case 3:
                if (follower.isBusy()){
                    follower.followPath(longSet,true);
                    setPathState(4);}
                break;
            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if (!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Grab, true);
                    autoScore.AutonIntake(1.0, -0.2);

                    setPathState(3);
                }
                break;
            case 5:

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (follower.isBusy()) {
                    /* Score Sample */

                    autoScore.AutonScore(out, 1, 1);
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(Score2, true);

                    setPathState(-1);
                }
                break;


        }
    }

    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        try {
            autonomousPathUpdate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        intake.init(hardwareMap);
        autoScore.init(hardwareMap);


    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {}
}