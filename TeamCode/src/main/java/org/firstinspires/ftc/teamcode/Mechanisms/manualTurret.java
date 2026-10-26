package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class manualTurret {
    private DcMotor turret, flywheel;

    public void init(HardwareMap hW){
        turret = hW.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void manual(double turretPower, double flywheelPower){
        flywheel.setPower(flywheelPower); // could just be button
        turret.setPower(turretPower); //need to be on smth analog

    }
}



