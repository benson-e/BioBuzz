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
         //took out midtake and gate so hardware map isnt twice
         flywheel = hwMap.get(DcMotor.class, "W5");
         flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         swivel = hwMap.get(DcMotor.class, "W6");
         swivel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

     }

     public void shooting(double midtakePower, double flywheelPower){
         midtake.setPower(midtakePower);
         flywheel.setPower(flywheelPower);
     }

     public void gateOpen(double gateAngle){
         gate.setPosition(gateAngle);
     }
}
