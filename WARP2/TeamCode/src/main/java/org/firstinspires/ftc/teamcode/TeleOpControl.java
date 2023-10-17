package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp Control")
//@Disabled
public class TeleOpControl extends OpMode {
    // Declare OpMode members here
    private ElapsedTime elapsedTime = new ElapsedTime();
    RobotMap robotMap = new RobotMap();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robotMap.init(hardwareMap);
    }

    @Override
    public void init_loop() {
    }
    public
    @Override
     void start() {
        elapsedTime.reset();
    }

    @Override
    public void loop() {
        //GAMEPAD 1
        double course = -gamepad1.left_stick_y;
        double rotate = (0.533333 * Math.pow(gamepad1.right_stick_x, 3) + 0.466666 * gamepad1.right_stick_x);
        double strafe = gamepad1.left_stick_x;

        if (Math.abs(rotate) > 0.1) {
            robotMap.leftDrive.setPower(rotate);
            robotMap.rightDrive.setPower(-rotate);
        } else {
            robotMap.leftDrive.setPower(course);
            robotMap.rightDrive.setPower(course);
        }

        //GAMEPAD 2

        if(Math.abs(gamepad2.right_stick_y) >= 0.05) {
            robotMap.clawMotor.setPower(-gamepad2.right_stick_y);
        } else {
            robotMap.clawMotor.setPower(0.0);
        }

        if(gamepad2.x) {
            robotMap.clawServo.setPosition(robotMap.CLAW_GRAB);
        } else if(gamepad2.b) {
            robotMap.clawServo.setPosition(robotMap.CLAW_RELEASE);
        }

    }

    @Override
    public void stop () {
        robotMap.clawMotor.setPower(0.0);
        robotMap.clawMotor.setPower(0.0);
    }
}

