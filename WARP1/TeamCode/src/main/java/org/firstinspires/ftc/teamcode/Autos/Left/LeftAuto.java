package org.firstinspires.ftc.teamcode.Autos.Left;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotMap;

@Autonomous(name="Left Auto", group="Left")
//@Disabled
public class LeftAuto extends LinearOpMode {

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
            robotMap.clawServo.setPosition(robotMap.CLAW_GRAB);
            sleep(1000);
            robotMap.liftForTime(0.7, 1.2);
            sleep(500);
            robotMap.quadMotorRunToDistance(17.0, 17.0, 0.5, 5.0);
            sleep(1000);

            telemetry.addData("Red", robotMap.color.red());
            telemetry.addData("Green", robotMap.color.green());
            telemetry.addData("Blue", robotMap.color.blue());
            telemetry.update();

            if (robotMap.color.green() > robotMap.color.red() && robotMap.color.green() > robotMap.color.blue()) {
                // Green - Center
                robotMap.quadMotorRunToDistance(8.0, 8.0, 0.5, 1.5);
            } else if (robotMap.color.blue() > robotMap.color.red() && robotMap.color.blue() > robotMap.color.green()) {
                // Blue - Field Right
                robotMap.quadMotorRunToDistance(8.0, 8.0, 0.5, 1.5);
                sleep(500);
                robotMap.strafeRunToDistance(22.0, 0.5,3.0);
            } else {
                // Red - Field Left
                robotMap.quadMotorRunToDistance(8.0, 8.0, 0.5, 1.5);
                sleep(500);
                robotMap.strafeRunToDistance(-22.0, 0.5,3.0);
            }

            sleep(30000);
        }
    }
}
