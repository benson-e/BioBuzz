package org.firstinspires.ftc.teamcode.pedroPathing;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
public class PedroBlue extends OpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private Follower follower;

    private final Pose startPose = poseFactory.of(85, 141, -90);
    private final Pose scorePose = poseFactory.of(85, 103, -90);
    private final Pose bridgeOne = poseFactory.of(117,103,0);
    private final Pose bridgeTwo = poseFactory.of(117,32,-90);
    private final Pose parkPose = poseFactory.of(135, 37, 0);


    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }
    private Path scoreToBridgeOne(){
        return line(scorePose, bridgeOne).linear(scorePose,bridgeOne);
    }
    private Path bridgeToBridge(){
        return line(bridgeOne, bridgeTwo).linear(bridgeOne,bridgeTwo);
    }
    private Path park(){
        return line(bridgeTwo, parkPose).linear(bridgeTwo, parkPose);
    }

    private Command autoRoutine() {
        return sequential(
                follow(follower, startToScore()),
                // score/shooting mechanism
                follow(follower, scoreToBridgeOne()),
                follow(follower, bridgeToBridge()),
                follow(follower, park())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

    }
}
