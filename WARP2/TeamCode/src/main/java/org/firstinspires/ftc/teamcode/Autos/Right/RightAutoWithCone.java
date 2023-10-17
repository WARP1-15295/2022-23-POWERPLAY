package org.firstinspires.ftc.teamcode.Autos.Right;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotMap;

@Autonomous(name="Right Auto With Cone", group="Right")
//@Disabled
public class RightAutoWithCone extends LinearOpMode {
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
            robotMap.dualMotorRunToDistance(-25.5, -25.5, 0.5, 5.0);
            sleep(1000);

            //store color
            red = robotMap.color.red();
            green = robotMap.color.green();
            blue = robotMap.color.blue();

            telemetry.addData("Red", red);
            telemetry.addData("Green", green);
            telemetry.addData("Blue", blue);
            telemetry.update();
            sleep(500);

            sleep(1000);
            robotMap.liftForTime(0.5, 4.5);
            sleep(1000);
            robotMap.dualMotorRunToDistance(-10.0, 10.0, 0.5, 5.0);
            sleep(1000);
            robotMap.liftForTime(-0.5, 1.0);
            sleep(1000);
            robotMap.clawServo.setPosition(robotMap.CLAW_RELEASE);
            sleep(1000);
            robotMap.liftForTime(0.5, 1.0);
            sleep(1000);
            robotMap.dualMotorRunToDistance(-10.0, 10.0, 0.5, 5.0);
            sleep(1000);
            robotMap.liftForTime(-0.5, 4.5);
            sleep(1000);

            if (green > red && green > blue) {
                // Green - Center

            } else if (blue > red && blue > green) {
                // Blue - Field Right
                //nothing

            } else {
                // Red - Field Left
            }










/*            robotMap.dualMotorRunToDistance(-17.0, -17.0, 0.3, 5.0);
            sleep(1000);

            telemetry.addData("Red", robotMap.color.red());
            telemetry.addData("Green", robotMap.color.green());
            telemetry.addData("Blue", robotMap.color.blue());
            telemetry.update();

            if (robotMap.color.green() > robotMap.color.red() && robotMap.color.green() > robotMap.color.blue()) {
                // Green - Center
                robotMap.dualMotorRunToDistance(-7.0, -7.0, 0.3, 5.0);
                sleep(500);
            } else if (robotMap.color.blue() > robotMap.color.red() && robotMap.color.blue() > robotMap.color.green()) {
                // Blue - Field Right
                robotMap.dualMotorRunToDistance(-4.0, -4.0, 0.3, 5.0);
                sleep(500);
                robotMap.dualMotorRunToDistance(11.5, -11.5, 0.3, 5.0);
                sleep(500);
                robotMap.dualMotorRunToDistance(-26.0, -26.0, 0.3, 5.0);
            } else {
                // Red - Field Left
                robotMap.dualMotorRunToDistance(-4.0, -4.0, 0.3, 5.0);
                sleep(500);
                robotMap.dualMotorRunToDistance(-11.5, 11.5, 0.3, 5.0);
                sleep(500);
                robotMap.dualMotorRunToDistance(-26.0, -26.0, 0.3, 5.0);
            }

            sleep(30000);*/
        }
    }
}
