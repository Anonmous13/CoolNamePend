package botwork.pedroPathing.constants;

import android.icu.text.MeasureFormat;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "fl";
        FollowerConstants.leftRearMotorName = "bl";
        FollowerConstants.rightFrontMotorName = "fr";
        FollowerConstants.rightRearMotorName = "br";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 13;

        FollowerConstants.xMovement = 58.49314300817902;
        FollowerConstants.yMovement = 39.80977742497427;
        FollowerConstants.forwardZeroPowerAcceleration = -52.31087325087609;
        FollowerConstants.lateralZeroPowerAcceleration = -93.82746990758454;

        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.useBrakeModeInTeleOp = true;

        //PIDF coeffi
        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.28,0.15,0.2,0);
    }
}
