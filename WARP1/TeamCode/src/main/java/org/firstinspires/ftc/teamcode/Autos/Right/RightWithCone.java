package org.firstinspires.ftc.teamcode.Autos.Right;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotMap;

@Autonomous(name="Right Auto With Cone", group="Right")
//@Disabled
public class RightWithCone extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    RobotMap robotMap = new RobotMap();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robotMap.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP) //DONE
        while (opModeIsActive()) {
            int red, green, blue;

            robotMap.clawServo.setPosition(robotMap.CLAW_GRAB);
            sleep(1000);
            robotMap.liftForTime(1.0, 0.2);
            sleep(500);
            robotMap.quadMotorRunToDistance(24.5, 24.5, 0.5, 5.0);
            sleep(1000);
            robotMap.liftForTime(0.7, 1.2);
            sleep(500);

            //store color
            red = robotMap.color.red();
            green = robotMap.color.green();
            blue = robotMap.color.blue();

            telemetry.addData("Red", red);
            telemetry.addData("Green", green);
            telemetry.addData("Blue", blue);
            telemetry.update();


            robotMap.strafeRunToDistance(-34.5, 0.5,3.0);
            sleep(1000);
            robotMap.liftForTime(0.6, 3.0);
            sleep(500);
            robotMap.axisServo.setPosition(robotMap.AXIS_LEFT);
            sleep(1000);
            robotMap.liftForTime(0.6, 1.0);
            sleep(500);
            robotMap.clawServo.setPosition(robotMap.CLAW_RELEASE);


            if (green > red && green > blue) {
                // Green - Center
                robotMap.strafeRunToDistance(33, 0.5, 5.0);

            } else if (blue > red && blue > green) {
                // Blue - Field Right
                robotMap.strafeRunToDistance(58, 0.5, 7.0);
                sleep(500);
                robotMap.quadMotorRunToDistance(1, 1, 0.3, 3.0);

            } else {
                // Red - Field Left
                robotMap.strafeRunToDistance(10, 0.5, 3.0);
                sleep(500);
                robotMap.quadMotorRunToDistance(1, 1, 0.3, 3.0);
            }

            sleep(500);

            robotMap.liftForTime(-0.5, 3.0);

            sleep(30000);

        }
    }
}
