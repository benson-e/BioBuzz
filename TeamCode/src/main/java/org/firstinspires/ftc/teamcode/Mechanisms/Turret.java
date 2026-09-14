package org.firstinspires.ftc.teamcode.Mechanisms;

import android.health.connect.datatypes.units.Power;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Turret {
    private DcMotor turret;
    private double kP = 0.0001;
    private double kD = 0.0000;
    private double goalX = 0;
    private double lastError = 0;
    private double angleTolerance = 0.2;
    private final double MAX_POWER = 0.6;
    private double power = 0;
    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap){
        turret = hwMap.get(DcMotor.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setkp (double newKP){
        kP= newKP;
    }

    public double getkP(){
        return kP;
    }

    public void setkD (double newKD){
        kD= newKD;
    }

    public double getkD(){
        return kD;
    }

    public void resetTimer(){
        timer.reset();
    }

    public void update(LLResult llresult ){
        double deltaTime = timer.seconds();
        timer.reset();

        if (llresult == null){
            turret.setPower(0);
            lastError = 0;
        }

        //________ Start PD CONTROLLER________

        double error = goalX - llresult.getTx();
        double pTerm = error * kP;

        double dTerm = 0;
        if (deltaTime > 0){
            dTerm = (error - lastError)/(deltaTime * kD);
        }

        if(Math.abs(error)< angleTolerance){
            power = 0;
        } else {
            power = Range.clip(pTerm + dTerm, -MAX_POWER, MAX_POWER);

        }

        // SAFETIES TO MAKE SURE YOU'RE NOT ROTATING PAST

        turret.setPower(power);
        lastError = error;
    }

}
