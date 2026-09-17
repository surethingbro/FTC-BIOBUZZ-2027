package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Mecanum extends OpMode {

    DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "intake"); // = -> asignar valor

        motor.setDirection(DcMotorSimple.Direction.REVERSE); // . -> acceder a un campo o a un metodo
    }

    @Override
    public void loop() {

        double power = gamepad1.left_stick_y;
        motor.setPower(power);
    }
}