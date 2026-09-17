package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class FieldCentric extends OpMode {

    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;

    GoBildaPinpointDriver pinpoint;

    @Override
    public void init() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "1");
        frontRight = hardwareMap.get(DcMotorEx.class, "2");
        backLeft = hardwareMap.get(DcMotorEx.class, "4");
        backRight = hardwareMap.get(DcMotorEx.class, "3");

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(9.6,15.7, DistanceUnit.CM);

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.resetPosAndIMU();

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {

        pinpoint.update();

        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        double heading = pinpoint.getPosition().getHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        frontLeft.setPower( (rotY + rotX + rx) / denominator);
        backLeft.setPower( (rotY - rotX + rx) / denominator);
        frontRight.setPower( (rotY - rotX - rx) / denominator);
        backRight.setPower((rotY + rotX - rx) / denominator);

        telemetry.addData("heading deg", pinpoint.getPosition().getHeading(AngleUnit.DEGREES));
        telemetry.addData("X", pinpoint.getPosition().getX(DistanceUnit.MM));
        telemetry.addData("Y", pinpoint.getPosition().getY(DistanceUnit.MM));

        telemetry.update();
    }
}
