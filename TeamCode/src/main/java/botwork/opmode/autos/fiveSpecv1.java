package botwork.opmode.autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import java.util.Timer;

@Autonomous(name = "5 Spec")
public class fiveSpecv1 extends OpMode {

    private DcMotorEx back;
    private CRServo bl, br;
    private Follower follower;
    private int pathState;
    public static double kP_o = 0.01;
    private Timer opmodeTimer, pathTimer;

    private final Pose startPose = new Pose(8, 65, Math.toRadians(180));
    private final Pose holdSpec = new Pose(33.465, 64.822, Math.toRadians(180));
    private final Pose scoreSpec = new Pose(38.804,65.27, Math.toRadians(180));
    private final Pose lineupOnePT1 = new Pose(55.385,34.846, Math.toRadians(0));
    private final Pose lineupOnePT2 = new Pose(58.154,24.692,Math.toRadians(0));
    private final Pose push1 = new Pose(12.692,24.461, Math.toRadians(0));


    @Override
    public void init() {
        back = hardwareMap.get(DcMotorEx.class, "back");
    }

    @Override
    public void loop() {

    }
}
