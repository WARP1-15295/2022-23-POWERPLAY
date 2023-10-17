package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotMap {

    /* Public OpMode members. */
    public static DcMotor leftDrive = null;
    public static DcMotor rightDrive = null;

    public  static DcMotor clawMotor = null;

    public static Servo clawServo = null;

    public static ColorSensor color = null;

    public static final double CLAW_RELEASE = 0.0;
    public static final double CLAW_GRAB = 1.0;

    public static final double TICKS_PER_REV = 537.6; //20:1 HD HEX MOTOR
    public static final int WHEEL_DIAMETER = 90; //REV ROBOTICS 90 MM OMNI-WHEEL
    public static final double MILLIMETER_TO_INCHES_CONSTANT = 25.4;
    public static final double SPROCKET_RATIO = 15.0 / 20.0;
    public static final double WHEEL_CIRCUMFERENCE_INCHES = (Math.PI * WHEEL_DIAMETER) / MILLIMETER_TO_INCHES_CONSTANT; // ~ 11.13 in. IN 1120 ticks (1 Rev)
    public static final double COUNTS_PER_INCH = (TICKS_PER_REV * SPROCKET_RATIO) / WHEEL_CIRCUMFERENCE_INCHES; //DRIVETRAIN's COUNTS PER INCH

    /* local OpMode members. */
    HardwareMap hardwareMap = null;
    private ElapsedTime runtime = new ElapsedTime();

    /* Constructor */
    public RobotMap() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
        hardwareMap = hwMap;

        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        clawMotor = hardwareMap.get(DcMotor.class, "armMotor");

        clawServo = hardwareMap.get(Servo.class, "clawServo");

        color = hardwareMap.get(ColorSensor.class, "color");
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        clawMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        clawServo.setDirection(Servo.Direction.FORWARD);

        // Reset motor encoders
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor default run mode to use encoders
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public int encoderConvert(double inches) {
        //The RUT_TO_POSITION function of DcMotor requires an int to be passed in. We cast the answer of this line down to an int to satisfy this.
        int units = (int) Math.round(COUNTS_PER_INCH * inches);
        return units;
    }

    public void dualMotorRunToDistance(double motor1DistanceInches, double motor2DistanceInches, double powerPercent, double timeoutSeconds) {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setTargetPosition(encoderConvert(motor1DistanceInches));
        rightDrive.setTargetPosition(encoderConvert(motor2DistanceInches));

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        runtime.startTime();
        leftDrive.setPower(powerPercent);
        rightDrive.setPower(powerPercent);

        while (runtime.seconds() <= timeoutSeconds && leftDrive.isBusy() && rightDrive.isBusy()) {
            //This waits until the timeout has ended or the motors are at the position, probably a better way to write this but it works.
        }

        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void liftForTime(double speed, double timeoutSeconds) {
        clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        runtime.reset();
        runtime.startTime();


        while (runtime.seconds() <= timeoutSeconds && leftDrive.isBusy() && rightDrive.isBusy()) {
            //This waits until the timeout has ended or the motors are at the position, probably a better way to write this but it works.
            clawMotor.setPower(speed);
        }

        clawMotor.setPower(0.0);
    }
/*

    public void singleMotorRunToDistance(double distanceInches, double timeoutSeconds) {
        middleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleMotor.setTargetPosition(encoderConvert(distanceInches));
        middleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();
        runtime.startTime();

        while (runtime.seconds() <= timeoutSeconds && middleMotor.isBusy()) {
            //This waits until the timeout has ended or the motor is at the position, probably a better way to write this but it works.
            if (runtime.seconds() <= 1.0) {
                middleMotor.setPower(0.25);
            }
            if (runtime.seconds() > 1.0 && runtime.seconds() < 1.5) {
                middleMotor.setPower(0.3);
            }

            if (runtime.seconds() >= 1.5 && runtime.seconds() <= 2.5) {
                middleMotor.setPower(0.35);
            }

            if (runtime.seconds() > 2.5 && runtime.seconds() < 3.5) {
                middleMotor.setPower(0.35);
            }

            if (runtime.seconds() >= 3.5 && runtime.seconds() <= 4.5) {
                middleMotor.setPower(0.35);
            }

            if (runtime.seconds() > 4.5) {
                middleMotor.setPower(0.35);
            }
        }

        middleMotor.setPower(0.0);
        middleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }*/

}
