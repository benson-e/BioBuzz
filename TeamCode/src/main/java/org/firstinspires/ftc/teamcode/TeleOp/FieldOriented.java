package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;

@TeleOp
public class FieldOriented extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    double forward, strafe, rotate;
    Intake intake = new Intake();//Intake (capital I) is the class lowercase intake is the name when referring to the instance created for this class
    double intakePower;

    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.a){
            intakePower = 1;
        } else if(gamepad1.b){
            intakePower = -1;
        }

        drive.driveFieldRelative(forward,strafe,rotate);
      //  intake.intake(); //intake in white refers to the method inside Intake

    }
}
