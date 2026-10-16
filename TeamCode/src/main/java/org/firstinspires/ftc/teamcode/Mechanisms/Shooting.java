package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooting {

    /*
    gate open(Servo)
    midtake going(DC)
    constant flywheel (DC)
    swiveling (DC)
    hard stop: 360 max
     */

     private DcMotor midtake, flywheel, swivel;
     private Servo gate;

     public void init(HardwareMap hwMap){
         midtake = hwMap.get(DcMotor.class, "W2");
         midtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         flywheel = hwMap.get(DcMotor.class, "W5");
         flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         swivel = hwMap.get(DcMotor.class, "W6");
         swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         gate = hwMap.get(Servo.class, "W4");
     }

     private void shooting(double midtakePower, double flywheelPower, double swivelPower){
         midtake.setPower(midtakePower);
         flywheel.setPower(flywheelPower);
         swivel.setPower(swivelPower);
     }

     private void gateOpen(double gateAngle){
         gate.setPosition(gateAngle);
     }
}
