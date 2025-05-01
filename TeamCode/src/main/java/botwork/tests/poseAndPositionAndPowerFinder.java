package botwork.tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Coordinate Finder")
public class poseAndPositionAndPowerFinder extends OpMode {
    private Follower follower;
    private DcMotorEx back, misumi;
    private CRServo claw, wrist, scorer, linearWrist;
    @Override
    public void init() {
//        back = hardwareMap.get(DcMotorEx.class, "back");
//        misumi = hardwareMap.get(DcMotorEx.class, "misumi");
//
//        claw = hardwareMap.get(CRServo.class, "claw");
//        wrist = hardwareMap.get(CRServo.class, "wrist");
//        scorer = hardwareMap.get(CRServo.class, "scorer");
//        linearWrist = hardwareMap.get(CRServo.class, "linearWrist");
//
//        back.setDirection(DcMotorSimple.Direction.REVERSE);
//        misumi.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        misumi.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        follower.update();
//        telemetry.addData("Misumi Slide Position", misumi.getCurrentPosition());
//        telemetry.addData("Linear Slide Position", back.getCurrentPosition());
//        telemetry.addData("Misumi Slide Power", back.getPower());
//        telemetry.addData("Linear Slide Power", back.getPower());
//        telemetry.addData("Claw Power", claw.getPower());
//        telemetry.addData("Wrist Power", wrist.getPower());
//        telemetry.addData("Swing Arm Power", linearWrist.getPower());
//        telemetry.addData("Swing Arm Servo", scorer.getPower());
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }
}
