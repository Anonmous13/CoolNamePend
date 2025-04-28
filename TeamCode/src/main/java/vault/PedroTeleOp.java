//package org.firstinspires.ftc.code.Bot.arsh;
//
//import static org.firstinspires.ftc.code.Bot.arsh.CompAutov1.AutoEndPose;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.PathChain;
//import com.pedropathing.pathgen.Point;
//import com.pedropathing.util.Constants;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import org.firstinspires.ftc.code.Pedro.constants.FConstants;
//import org.firstinspires.ftc.code.Pedro.constants.LConstants;
//
//@TeleOp(name = "TeleOp v2")
//
//public class Telev2 extends LinearOpMode {
//    public static double kP_i = 0.02;
//    public static double kP_o = 0.01;
//
//    private Follower follower;
//    private PathChain specHang, humanP, lineS, getS;
//
//    private final Pose getSpec = new Pose(2, 29, Math.toRadians(0));
//    private Pose spec = new Pose(38.4, 64, Math.toRadians(180));
//    private final Pose lineSpec = new Pose(11.5,29,Math.toRadians(0));
//    private final Pose start = new Pose (38, 60, Math.toRadians(180));
//
//    public void buildPaths() {
//
//        humanP = follower.pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Point(follower.getPose()),
//                                new Point(72,0,Point.CARTESIAN),
//                                new Point(lineSpec)))
//                .setLinearHeadingInterpolation(follower.getPose().getHeading(),180)
//                .build();
//
//        lineS = follower.pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Point(follower.getPose()),
//                                new Point(32.1,38.6,Point.CARTESIAN),
//                                new Point(lineSpec)))
//                .setLinearHeadingInterpolation(follower.getPose().getHeading(),getSpec.getHeading())
//                .build();
//
//        getS = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(
//                                new Point(follower.getPose()),
//                                new Point(getSpec)))
//                .setLinearHeadingInterpolation(follower.getPose().getHeading(),getSpec.getHeading())
//                .build();
//
//        specHang = follower.pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Point(follower.getPose()),
//                                new Point(28.4,36.4,Point.CARTESIAN),
//                                new Point(13,43,Point.CARTESIAN),
//                                new Point(spec)))
//                .setLinearHeadingInterpolation(follower.getPose().getHeading(), spec.getHeading())
//                .build();
//
//    }
//
//    public enum State {
//        HOME, // where everything is idle and retracted
//        SCORE,
//        INTAKE,
//    }
//
//    State currentState = State.HOME;
//
//
//    public static double intakeTarget = 0;
//    public static double outtakeTarget = 0;
//
//    public static double intakeOut = 750;
//    public static double intakeIn = 0;
//
//    public static double outtakeIn = 0;
//    public static double outtakeSpec = 2600;
//
//    public CRServo bl, br, fl, fr, wrist, lm, rm; // back left gecko, back right gecko, front left gecko, front right gecko, wrist, left misumi, right misumi
//
//    @Override
//    public void runOpMode() {
//
//        Constants.setConstants(FConstants.class, LConstants.class);
//
//        follower = new Follower(hardwareMap);
//        follower.startTeleopDrive();
//        follower.setStartingPose(start);
//        buildPaths();
//
//        // Initialize slide motors
//        DcMotor back = hardwareMap.get(DcMotor.class, "back");
//        DcMotor lr = hardwareMap.get(DcMotor.class, "lr");
//        // Init servos
//        bl = hardwareMap.get(CRServo.class, "br");
//        br = hardwareMap.get(CRServo.class, "bl");
//
//        fr = hardwareMap.get(CRServo.class, "fr");
//        fl = hardwareMap.get(CRServo.class, "fl");
//
//        wrist = hardwareMap.get(CRServo.class, "wrist");
//
//        lm = hardwareMap.get(CRServo.class, "lm");
//        rm = hardwareMap.get(CRServo.class, "rm");
//
//        back.setDirection(DcMotorSimple.Direction.REVERSE);
//        back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        lr.setDirection(DcMotorSimple.Direction.REVERSE);
//        lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        // Ensure motor resists motion when idle
//        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        waitForStart();
//
//
//        if (isStopRequested()) return;
//
//        while (opModeIsActive()) {
//
//            switch (currentState) {
//                case HOME:
//                    intakeTarget = intakeIn;
//                    outtakeTarget = outtakeIn;
//
//                    controlSpec(gamepad2);
//                    controlSample(gamepad2);
//                    controlIntake(gamepad2);
//                    controlWrist(gamepad2);
//
//                    if (gamepad2.y) {
//                        currentState = State.SCORE;
//                    }
//
//                    if (gamepad2.left_bumper) {
//                        currentState = State.INTAKE;
//                    }
//
//                    break;
//
//                case SCORE:
//                    outtakeTarget = outtakeSpec;
//                    controlSpec(gamepad2);
//
//                    if (gamepad2.right_bumper) {
//                        outtakeTarget+=350;
//                    }
//
//                    if (gamepad2.a) {
//                        currentState = State.HOME;
//                    }
//                    break;
//
//                case INTAKE:
//                    intakeTarget = intakeOut;
//                    controlIntake(gamepad2);
//                    controlWrist(gamepad2);
//                    controlSample(gamepad2);
//
//
//                    if (gamepad2.a) {
//                        upIntake();
//                        currentState = State.HOME;
//                    }
//                    break;
//
//
//            }
//
//            // drive
//            if(!follower.isBusy()) {
//                follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
//            }
//
//            if(gamepad1.x){
//                currentState = State.SCORE;
//                controlSpec(gamepad2);
//                follower.followPath(specHang,1.0,true);
//                spec = new Pose (spec.getX(),spec.getY()+1,spec.getHeading());
//            }
//
//            if(gamepad1.b){
//                currentState = State.HOME;
//                follower.followPath(lineS,1.0,true);
//            }
//
//            if(gamepad1.right_bumper){
//                follower.followPath(getS,0.5,true);
//            }
//
//            if(gamepad1.left_bumper){
//                follower.followPath(humanP,1.0,true);
//            }
//
//            if(gamepad1.right_stick_button){
//                follower.turnDegrees(180, true);
//            }
//
//            if(gamepad1.y){
//                follower.breakFollowing();
//                follower.setMaxPower(1.0);
//                follower.startTeleopDrive();
//            }
//
//            follower.update();
//
//            back.setPower(outtakePid(outtakeTarget, back.getCurrentPosition()));
//            lr.setPower(intakePid(intakeTarget, lr.getCurrentPosition()));
//
//            telemetry.addData("Back Slides Position: ", back.getCurrentPosition());
//            telemetry.addData("Intake Slides Position: ", lr.getCurrentPosition());
//            telemetry.addData("State: ", currentState);
//            telemetry.addLine("");
//            telemetry.addData("X", follower.getPose().getX());
//            telemetry.addData("Y", follower.getPose().getY());
//            telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
//
//
//            telemetry.update();
//        }
//    }
//
//
//    public double intakePid(double target, double current) {
//        return (target - current) * kP_i;
//    }
//
//    public double outtakePid(double target, double current) {
//        return (target - current) * kP_o;
//    }
//
//
//    // method cult
//    public void outtakeSpec() {
//        bl.setPower(-1.0);
//        br.setPower(1.0);
//    }
//
//    public void intakeSpec() {
//        bl.setPower(1.0);
//        br.setPower(-1.0);
//    }
//
//    public void idleSpec() {
//        bl.setPower(0);
//        br.setPower(0);
//    }
//
//    public void intakeSample() {
//        fl.setPower(-1.0);
//        fr.setPower(1.0);
//    }
//
//    public void outtakeSample() {
//        fl.setPower(1.0);
//        fr.setPower(-1.0);
//    }
//
//    public void idleSample() {
//        fl.setPower(0);
//        fr.setPower(0);
//    }
//
//    public void leftWrist() {
//        wrist.setPower(0.5);
//    }
//
//    public void rightWrist() {
//        wrist.setPower(-0.5);
//    }
//
//    public void idleWrist() {
//        wrist.setPower(0);
//    }
//
//    public void downIntake() {
//        lm.setPower(1);
//        rm.setPower(-1);
//    }
//
//    public void upIntake() {
//        rm.setPower(1);
//        lm.setPower(-1);
//
//    }
//
//    public void idleIntake(){
//        rm.setPower(0);
//        lm.setPower(0);
//    }
//
//
//    public void controlSpec(Gamepad gamepad) {
//        if (gamepad2.b) {
//            intakeSpec();
//        } else if (gamepad2.x) {
//            outtakeSpec();
//        } else {
//            idleSpec();
//        }
//    }
//
//    public void controlSample(Gamepad gamepad) {
//        if (gamepad2.right_stick_button) {
//            intakeSample();
//        } else if (gamepad2.left_stick_button) {
//            outtakeSample();
//        } else {
//            idleSample();
//        }
//    }
//
//    public void controlWrist(Gamepad gamepad) {
//        if (gamepad2.dpad_left) {
//            leftWrist();
//        } else if (gamepad2.dpad_right) {
//            rightWrist();
//        } else {
//            idleWrist();
//        }
//    }
//
//    public void controlIntake(Gamepad gamepad) { //check
//        if (gamepad2.dpad_down ) {
//            downIntake();
//        } else if (gamepad2.dpad_up) {
//            upIntake();
//        } else {
//            idleIntake();
//        }
//    }
//}
