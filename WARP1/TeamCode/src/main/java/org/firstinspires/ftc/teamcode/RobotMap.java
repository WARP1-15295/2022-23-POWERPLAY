package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotMap {

    /* Public OpMode members. */
    public static DcMotor leftFrontDrive = null;
    public static DcMotor rightFrontDrive = null;
    public static DcMotor leftBackDrive = null;
    public static DcMotor rightBackDrive = null;

    public static DcMotor liftMotor = null;

    public static Servo axisServo = null;
    public static Servo clawServo = null;

    public static ColorSensor color = null;

    public static final double CLAW_GRAB = 0.7;
    public static final double CLAW_RELEASE = 0.4;
    public static final double AXIS_LEFT = 0.15;
    public static final double AXIS_MIDDLE = 0.5;
    public static final double AXIS_RIGHT = 0.85;


    public static final double TICKS_PER_REV = 537.6; //20:1 HD HEX MOTOR
    public static final double WHEEL_DIAMETER = 4.0; //VEX 4 in. MECH WHEELS
    public static final double WHEEL_CIRCUMFERENCE_INCHES = Math.PI * WHEEL_DIAMETER; // ~ 12.57 in. IN 560 ticks (1 Rev)
    public static final double COUNTS_PER_INCH = TICKS_PER_REV / WHEEL_CIRCUMFERENCE_INCHES; //DRIVETRAIN's COUNTS PER INCH

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

        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        axisServo = hardwareMap.get(Servo.class, "axisServo");
        clawServo = hardwareMap.get(Servo.class, "clawServo");

        color = hardwareMap.get(ColorSensor.class, "color");

        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        axisServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setDirection(Servo.Direction.FORWARD);

        // Reset motor encoders
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor default run mode to use encoders
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    /**
     * Method that converts from inches to encoder units
     *
     * @param inches Distance to travel
     * @return encoder units
     */
    public int encoderConvert(double inches) {
        int units = (int) Math.round(COUNTS_PER_INCH * inches); //The RUT_TO_POSITION function of DcMotor requires an int to be passed in. We cast the answer of this line down to an int to satisfy this.
        return units;
    }

    public void strafeRunToDistance(double distanceInches, double powerPercent, double timeoutSeconds) {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*leftFrontDrive.setTargetPosition(encoderConvertStrafe(distanceInches));
        leftBackDrive.setTargetPosition(encoderConvertStrafe(-distanceInches));
        rightFrontDrive.setTargetPosition(encoderConvertStrafe(-distanceInches));
        rightBackDrive.setTargetPosition(encoderConvertStrafe(distanceInches));
         */

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        runtime.startTime();
        leftFrontDrive.setPower(powerPercent);
        leftBackDrive.setPower(powerPercent);
        rightFrontDrive.setPower(powerPercent);
        rightBackDrive.setPower(powerPercent);

        while (runtime.seconds() <= timeoutSeconds && leftFrontDrive.isBusy() && rightFrontDrive.isBusy()) {
            //This waits until the timeout has ended or the motors are at the position, probably a better way to write this but it works.
        }

        leftFrontDrive.setPower(0.0);
        leftBackDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Function for the robot to drive straight or rotate in place.
     *
     * @param leftDistanceInches Distance for left motors to travel
     * @param rightDistanceInches Distance for right motors to travel
     * @param powerPercent Power to run at
     * @param timeoutSeconds Timeout in Seconds
     */
    public void quadMotorRunToDistance(double leftDistanceInches, double rightDistanceInches, double powerPercent, double timeoutSeconds) {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setTargetPosition(encoderConvert(leftDistanceInches));
        leftBackDrive.setTargetPosition(encoderConvert(leftDistanceInches));
        rightFrontDrive.setTargetPosition(encoderConvert(rightDistanceInches));
        rightBackDrive.setTargetPosition(encoderConvert(rightDistanceInches));

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        runtime.startTime();
        leftFrontDrive.setPower(powerPercent);
        leftBackDrive.setPower(powerPercent);
        rightFrontDrive.setPower(powerPercent);
        rightBackDrive.setPower(powerPercent);

        while (runtime.seconds() <= timeoutSeconds && leftFrontDrive.isBusy() && rightFrontDrive.isBusy() && leftBackDrive.isBusy() && rightBackDrive.isBusy()) {
            //This waits until the timeout has ended or the motors are at the position, probably a better way to write this but it works.
        }

        leftFrontDrive.setPower(0.0);
        leftBackDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void liftForTime(double power, double timeoutSeconds) {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        runtime.reset();
        runtime.startTime();

        liftMotor.setPower(power);
        while (runtime.seconds() <= timeoutSeconds) {
            //This waits until the timeout has ended or the motors are at the position, probably a better way to write this but it works.
        }
        liftMotor.setPower(0.0);
    }
}
