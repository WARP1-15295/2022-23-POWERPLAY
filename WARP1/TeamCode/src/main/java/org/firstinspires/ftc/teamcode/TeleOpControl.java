package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.lang.Math;

import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp")
//@Disabled
public class TeleOpControl extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    RobotMap robotMap = new RobotMap();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robotMap.init(hardwareMap);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //GAMEPAD 1
        double degreesToRads45 = -Math.PI/4;
        double leftStickDeadzone = 0.1;
        double rightStickDeadzone = 0.1;

        double x = gamepad1.left_stick_x * Math.cos(degreesToRads45) + gamepad1.left_stick_y * Math.sin(degreesToRads45);
        double y = -gamepad1.left_stick_y * Math.cos(degreesToRads45) + gamepad1.left_stick_x * Math.sin(degreesToRads45);
        double rotate = gamepad1.right_stick_x;

        if (Math.abs(rotate) > rightStickDeadzone) {
            robotMap.leftFrontDrive.setPower(rotate);
            robotMap.leftBackDrive.setPower(rotate);
            robotMap.rightBackDrive.setPower(-rotate);
            robotMap.rightFrontDrive.setPower(-rotate);
        } else if(Math.abs(x) > leftStickDeadzone || Math.abs(y) > leftStickDeadzone){
            robotMap.leftFrontDrive.setPower(x);
            robotMap.leftBackDrive.setPower(y);
            robotMap.rightFrontDrive.setPower(y);
            robotMap.rightBackDrive.setPower(x);
        } else {
            robotMap.leftFrontDrive.setPower(0.0);
            robotMap.leftBackDrive.setPower(0.0);
            robotMap.rightFrontDrive.setPower(0.0);
            robotMap.rightBackDrive.setPower(0.0);
        }

        //GAMEPAD 2
        if (Math.abs(gamepad2.right_stick_y) > rightStickDeadzone) {
            robotMap.liftMotor.setPower(-gamepad2.right_stick_y);
        } else {
            robotMap.liftMotor.setPower(0);
        }

        if (gamepad2.dpad_left) {
            robotMap.axisServo.setPosition(robotMap.AXIS_LEFT);
        } else if (gamepad2.dpad_up) {
            robotMap.axisServo.setPosition(robotMap.AXIS_MIDDLE);
        } else if(gamepad2.dpad_right) {
            robotMap.axisServo.setPosition(robotMap.AXIS_RIGHT);
        }

        if (gamepad2.x) {
            robotMap.clawServo.setPosition(robotMap.CLAW_GRAB);
        } else if (gamepad2.b) {
            robotMap.clawServo.setPosition(robotMap.CLAW_RELEASE);
        }
    }

    @Override
    public void stop() {
    }
}
