package botwork.exampleAutosAndTeles;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

/**
 * This is a TeleOp program made for testing and driving the robot on the "Into the Deep" FTC mat.
 * It was not made during that season, but we used the field as a reference to test movement and
 * control. It uses gamepad joysticks to move the robot with four motors, letting it drive in all
 * directions (forward, backward, strafe, and turn).
 *
 * This program is mainly for practicing and getting the basic drive code working. It will be
 * updated and improved for the next FTC season called "DECODE™," where the field and objectives
 * will be different. The current setup includes motor direction settings, brake behavior, and
 * clips the power between -1.0 and 1.0 so the robot drives safely.
 *
 * More features like speed control, mechanism control, and FSM (Finite State Machine) behavior
 * might be added later depending on the needs of the upcoming season.
 * Another key thing. During the actual season this will be our teleop with more features
 *
 * @author Hamza Chandia
 * @version 1.0, 04/19/2025
 */


@TeleOp(name = "TeleOp with FSM")
public class CNP_TELE extends OpMode {
    // Variables for assigning power easily.
    private double flPower, frPower, blPower, brPower;
    // Defining the motors
    private DcMotorEx frontRight, frontLeft, backRight, backLeft = null;

    // Enum for state machine (not used in this code, but defined enums listed)
    public enum SlideState {
        HOME,
        BASKET,
        INTAKE
    }

    @Override
    public void init() {
        // Hardware map - assigns hardware names from the config
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");//front right 0
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");// front left 1
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");//back left 2
        backRight = hardwareMap.get(DcMotorEx.class, "br");//back right 3

        //resets encoder for accurate positioning
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


        // Set motor directions
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);

        // Set zero power behavior to BRAKE
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // Equations for movement with joystick input
        flPower = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
        blPower = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
        frPower = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
        brPower = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
        // Clip power values to be between -1.0 and 1.0
        flPower = Range.clip(flPower, -1.0, 1.0);
        blPower = Range.clip(blPower, -1.0, 1.0);
        frPower = Range.clip(frPower, -1.0, 1.0);
        brPower = Range.clip(brPower, -1.0, 1.0);
        // Set motor power
        frontLeft.setPower(flPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);
        frontRight.setPower(frPower);

        //telemetry for debugging
        //motor position telemetry
        telemetry.addData("Front Left Motor Position:", frontLeft.getCurrentPosition());
        telemetry.addData("Front Right Motor Position:", frontRight.getCurrentPosition());
        telemetry.addData("Back Right Motor Position:", backRight.getCurrentPosition());
        telemetry.addData("Back Left Motor Position:", backLeft.getCurrentPosition());
        //motor velocity telemetry
        telemetry.addData("Bl Velocity:", backLeft.getVelocity());
        telemetry.addData("Br Motor Velocity", backRight.getVelocity());
        telemetry.addData("Fr velocity", frontRight.getVelocity());
        telemetry.addData("Fl Velocity", frontLeft.getVelocity());
    }

}m

