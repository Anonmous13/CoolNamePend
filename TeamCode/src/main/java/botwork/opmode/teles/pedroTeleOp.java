package botwork.opmode.teles;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import botwork.pedroPathing.constants.FConstants;
import botwork.pedroPathing.constants.LConstants;

@TeleOp(name = "Pedro TeleOp")
public class pedroTeleOp extends OpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    private DcMotorEx back, misumi;
    private CRServo misumiWrist, misumiIntake, specWrist, specScorer;
    double viperPower, misumiPower;

    public enum SlideState {
        HOME,
        SCORE,
        INTAKE
    }

    SlideState slideState = SlideState.HOME;
    
    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        //
        misumiWrist = hardwareMap.get(CRServo.class,"wrist");
        misumiIntake = hardwareMap.get(CRServo.class,"axon intake");
        specWrist = hardwareMap.get(CRServo.class,"specWrist");
        specScorer = hardwareMap.get(CRServo.class,"specScorer");
        //hardware map motors
        back = hardwareMap.get(DcMotorEx.class,"back");
        misumi = hardwareMap.get(DcMotorEx.class,"misumi");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {

        switch(slideState) {
            case SCORE:
                if (gamepad2.x) {
                back.setTargetPosition(3453);
            }
            case INTAKE:
                if (gamepad1.y) {
                    misumi.setTargetPosition(984);
                }
            case HOME:
                if (gamepad2.b) {
                    back.setTargetPosition(0);
                    misumi.setTargetPosition(0);
                }
        }
        double wrist = 0.0;
        double intake = 0.0;
        double linearWrist = 0.0;
        double scorer = 0.0;

        viperPower = gamepad2.left_stick_y;
        misumiPower = gamepad2.right_stick_y;
        misumi.setPower(misumiPower);
        back.setPower(viperPower);

        //intake with misumi
        if (gamepad2.a) {
            intake = 1.0;
        //spitting out samples
        } else if(gamepad2.b) {
            intake = -1.0;
        }

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Linear Slide Position:", back.getCurrentPosition());
        telemetry.addData("Misumi Slide Position", misumi.getCurrentPosition());
        telemetry.addData("Linear Slide Power", back.getPower());
        telemetry.update();



    }

    @Override
    public void stop() {
    }
}