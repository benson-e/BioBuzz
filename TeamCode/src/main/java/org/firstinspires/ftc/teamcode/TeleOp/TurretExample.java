package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Mechanisms.Turret;
import org.firstinspires.ftc.teamcode.limelight.AprilTagLimelightMT1;

public class TurretExample extends OpMode {
    private Turret turret = new Turret();
    Limelight3A limelight;
    private IMU imu;

double[] stepSizes = {0.1, 0.001, 0.0001, 0.00001, 0.000001};
int stepIndex = 2;




    @Override
    public void init() {
        //limelight init
        turret.init(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        //april tag pipeline changes in the limelight setup
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        telemetry.addLine("initialization complete");
    }

    public void start(){
        turret.resetTimer();
    }

    @Override
    public void loop() {
        //vision logic
        LLResult result = limelight.getLatestResult();
        turret.update(result);

        if (gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length; // cycle through the step index
        }

        if(gamepad1.dpadLeftWasPressed()){
            turret.setkp(turret.getkP() - stepSizes[stepIndex]);
        }

        if(gamepad1.dpadRightWasPressed()){
            turret.setkp(turret.getkP() + stepSizes[stepIndex]);
        }

        if(gamepad1.dpadDownWasPressed()){
            turret.setkD(turret.getkD() - stepSizes[stepIndex]);
        }

        if(gamepad1.dpadUpWasPressed()){
            turret.setkD(turret.getkD() - stepSizes[stepIndex]);
        }


        if (result != null && result.isValid()) {
            Pose3D botpose = result.getBotpose();
            double tx = result.getTx();

            telemetry.addData("TX",tx);
        }else{
            telemetry.addLine("NO APRIL TAG DETECTED");
        }
        telemetry.addLine("__________________________");
        telemetry.addData("Tuning P","%.6f (D-Pad L/R)",turret.getkP());
        telemetry.addData("Tuning D","%.6f (D-Pad U/D)",turret.getkD());
        telemetry.addData("Step Size","%.6f (B Button)", stepSizes[stepIndex]);


    }

}
