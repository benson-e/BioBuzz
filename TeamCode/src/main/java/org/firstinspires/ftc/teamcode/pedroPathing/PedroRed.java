package org.firstinspires.ftc.teamcode.pedroPathing;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
@Autonomous
public class PedroRed extends OpMode {
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    //the poses and their value aka position, add all the spots the robot go through
    private final Pose startPose = poseFactory.of(59,2, 90);
    private final Pose scorePose = poseFactory.of(59, 40,90);
    private final Pose bridge1 = poseFactory.of(28, 40, 180);
    private final Pose bridge2 = poseFactory.of(28, 103, 90 );
    private final Pose parkPose = poseFactory.of(6, 103, 180);

    //path methods, aka one point to another
    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }
    private Path scoreToBridge1(){
        return line(scorePose, bridge1).linear(scorePose, bridge1);
    }
    private Path bridge1ToBridge2(){
        return line(bridge1, bridge2).linear(bridge1, bridge2);
    }
    private Path bridge2ToPark(){
        return line(bridge2, parkPose).linear(bridge2, parkPose);
    }

    private Command autoRoutine() {
        return sequential( //this makes it go in order, not repeated/loop
                follow(follower, startToScore()),
                // score/shooting mechanism, like the turret
                follow(follower, scoreToBridge1()),
                follow(follower, bridge1ToBridge2()),
                follow(follower, bridge2ToPark())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);// makes sure that the robot starts at the startPose/location
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine()); //activate the sequential work/go/start/
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

    }
}











