package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private DcMotor intake, midtake;
    private CRServo sidetake1, sidetake2;
    private Servo gate;
//creates the variables for the motors

    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "W1");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midtake = hwMap.get(DcMotor.class, "W2");
        midtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sidetake1 = hwMap.get(CRServo.class, "sl");
        sidetake2 = hwMap.get(CRServo.class, "sr");
        gate = hwMap.get(Servo.class, "W4");
    }
    /*gets the names of the motors to allow for communication with the robot and
    tells the Dc motors to use an encoder for more accuracy */
    public void intake(double intakePower, double midtakePower, double sidetakePower){
        intake.setPower(intakePower);
        midtake.setPower(midtakePower);
        sidetake1.setPower(sidetakePower);
        sidetake2.setPower(-sidetakePower);
    }
    //sets the power of the different motors to control speed
    public void gateClose(double gateAngle){
        gate.setPosition(gateAngle);
    }
    //sets the angle of the gate to closed while the intake is running
}
