package Hamza.botwork.FSM;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * This is a TeleOp program made for testing and driving the robot on the "Into the Deep" FTC mat.
 * It was not made during that season, but we used the field as a reference to test movement and
 * control. It uses gamepad joysticks to move the robot with four motors, letting it drive in all
 * directions (forward, backward, strafe, and turn).
 *
 * This program is mainly for practicing and getting the basic drive code working. It will be
 * updated and improved for the next FTC season called "FIRST Age," where the field and objectives
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
    private DcMotor frontRight, frontLeft, backRight, backLeft = null;
    // Enum for state machine (not used in this snippet, but defined)
    public enum SlideState {
        HOME,
        BASKET,
        INTAKE
    }
    @Override
    public void init() {
        // Hardware map - assigns hardware names from the config
        frontRight = hardwareMap.get(DcMotor.class,"fr");//front right 0
        frontLeft = hardwareMap.get(DcMotor.class,"fl");// front left 1
        backLeft = hardwareMap.get(DcMotor.class,"bl");//back left 2
        backRight = hardwareMap.get(DcMotor.class,"br");//back right 3

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set motor directions
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set zero power behavior to BRAKE
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    }
}
