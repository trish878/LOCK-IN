

package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class WORLDSAUTO extends LinearOpMode {

    public class VertSlides {
        private DcMotorEx vertSlideLeft, vertSlideRight;

        public VertSlides(HardwareMap hardwareMap) {
            vertSlideLeft = hardwareMap.get(DcMotorEx.class, "horizontal");
            vertSlideLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            vertSlideRight = hardwareMap.get(DcMotorEx.class, "vertSlideRight");
            vertSlideRight.setDirection(DcMotorSimple.Direction.FORWARD);

        }

        public class LiftUp implements Action {
            private boolean initialized = false;
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    vertSlideLeft.setPower(constants.SLIDE_UP_POWER);
                    vertSlideRight.setPower(constants.SLIDE_UP_POWER);
                    initialized = true;
                }

                if (vertSlideLeft.getCurrentPosition() < constants.SLIDES_SLAM_UP_POS + 200) {
                    return true;
                } else {
                    vertSlideLeft.setPower(0.075);
                    vertSlideRight.setPower(0.075);
                    return false;
                }
            }
        }
        public Action liftUp() {
            return new LiftUp();
        }

        public class LiftPlaceDown implements Action {
            private boolean initialized = false;
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    vertSlideLeft.setPower(constants.SLIDE_DOWN_POWER);
                    vertSlideRight.setPower(constants.SLIDE_DOWN_POWER);
                    initialized = true;
                }

                if (vertSlideLeft.getCurrentPosition() > constants.SLIDES_SLAM_DOWN_POS + 500) {
                    return true;
                } else {
                    vertSlideLeft.setPower(0);
                    vertSlideRight.setPower(0);
                    return false;
                }
            }
        }
        public Action liftPlaceDown() {
            return new LiftPlaceDown();
        }

        public class LiftDown implements Action {
            private boolean initialized = false;
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    vertSlideLeft.setPower(constants.SLIDE_DOWN_POWER);
                    vertSlideRight.setPower(constants.SLIDE_DOWN_POWER);
                    initialized = true;
                }

                if (vertSlideLeft.getCurrentPosition() > constants.SLIDES_SLAM_DOWN_POS) {
                    return true;
                } else {
                    vertSlideLeft.setPower(0);
                    vertSlideRight.setPower(0);
                    vertSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    return false;
                }
            }
        }
        public Action liftDown() {
            return new LiftDown();
        }


    }

    public class HorSlides {
        DcMotorEx horSlide;

        public HorSlides(HardwareMap hardwareMap) {
            horSlide = hardwareMap.get(DcMotorEx.class, "vertSlideLeft");
        }

        public class HoldIn implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                horSlide.setPower(-0.1);
                return true;
            }
        }

        public Action holdIn() {
            return new HoldIn();
        }

    }
    public class OuttakeArm {
        Servo outtakeArmLeft, outtakeArmRight, outtakeArmWrist;

        public OuttakeArm(HardwareMap hardwareMap) {
            outtakeArmLeft = hardwareMap.get(Servo.class, "rotateLeft");
            outtakeArmLeft.setDirection(Servo.Direction.REVERSE);

            outtakeArmRight = hardwareMap.get(Servo.class, "rotateRight");
            outtakeArmRight.setDirection(Servo.Direction.FORWARD);

            outtakeArmWrist = hardwareMap.get(Servo.class, "rotateClaw");
            outtakeArmWrist.setDirection(Servo.Direction.FORWARD);
        }

        public class IntakePos implements Action {
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeArmLeft.setPosition(constants.ROTATE_ARM_INTAKE_POS);
                outtakeArmRight.setPosition(constants.ROTATE_ARM_INTAKE_POS);
                outtakeArmWrist.setPosition(constants.ROTATE_CLAW_INTAKE_POS);
                return false;
            }
        }
        public Action intakePos() {
            return new IntakePos();
        }

        public class OuttakePos implements Action {
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeArmLeft.setPosition(constants.ROTATE_ARM_OUTTAKE_POS + constants.ROTATE_ARM_SHIFT);
                outtakeArmRight.setPosition(constants.ROTATE_ARM_OUTTAKE_POS);
                outtakeArmWrist.setPosition(constants.ROTATE_CLAW_OUTTAKE_POS);
                return false;
            }
        }
        public Action OuttakePos() {
            return new OuttakePos();
        }
    }
    public class OuttakeClaw {
        Servo outtakeClaw;

        public OuttakeClaw(HardwareMap hardwareMap) {
            outtakeClaw = hardwareMap.get(Servo.class, "OuttakeClaw");
            outtakeClaw.setDirection(Servo.Direction.REVERSE);
        }

        public class OpenClaw implements Action {
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeClaw.setPosition(constants.OUTTAKE_CLAW_OPEN_POS);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

        public class CloseClaw implements Action {
            WORLDSCONSTANTS constants = new WORLDSCONSTANTS();
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeClaw.setPosition(constants.OUTTAKE_CLAW_CLOSE_POS);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        //TODO: CONFIGURE VARIABLES
        int x = 20;
        int y = -63;

        Pose2d initalPose = new Pose2d(x, y, Math.toRadians(-90));

        //TODO: SETUP
        MecanumDrive drive = new MecanumDrive(hardwareMap, initalPose);
        VertSlides vertSlides = new VertSlides(hardwareMap);
        OuttakeArm outtakeArm = new OuttakeArm(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        HorSlides horSlides = new HorSlides(hardwareMap);

        //TODO: INIT
        Actions.runBlocking(
                new ParallelAction(
                        outtakeClaw.closeClaw()
                )
        );

        vertSlides.vertSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //TODO: PATHS

        // Goes to the Submursible from Start
        TrajectoryActionBuilder Path1 = drive.actionBuilder(initalPose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-30 + x,45 + y), Math.toRadians(90));

        TrajectoryActionBuilder Path2 = Path1.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(10 + x, 25+y), Math.toRadians(90))
                .setTangent(90) //TODO
                .splineToConstantHeading(new Vector2d(23+x, 55+y), Math.toRadians(0))
                .strafeTo(new Vector2d(20+x, 20 + y))

                .setTangent(90)
                .splineToConstantHeading(new Vector2d(33+x, 55+y), Math.toRadians(0))
                .strafeTo(new Vector2d(30+x, 20 + y))

                .waitSeconds(0.25)
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(12+x, y), Math.toRadians(-90))
                ;

        TrajectoryActionBuilder Path3 = Path2.endTrajectory().fresh()
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-32 + x,51 + y), Math.toRadians(90));

        TrajectoryActionBuilder Path4 = Path3.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(33 + x, 5+y), Math.toRadians(-90));

        TrajectoryActionBuilder Path5 = Path4.endTrajectory().fresh()
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-22 + x,63 + y), Math.toRadians(90));

        TrajectoryActionBuilder Path6 = Path5.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(37 + x, 35+y), Math.toRadians(-90));



        waitForStart();

        //TODO AUTO

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(

                                Path1.build(),
                                // Goes to Submursible to place the first specimen
                                new ParallelAction(
                                        vertSlides.liftUp(),
                                        outtakeArm.OuttakePos()

                                ),
                                new SleepAction(0.25),
                                vertSlides.liftPlaceDown(),
                                outtakeClaw.openClaw(),
                                vertSlides.liftDown(),
                                outtakeArm.intakePos(),

                                Path2.build(),

                                outtakeClaw.closeClaw(),
                                outtakeArm.OuttakePos(),
                                new ParallelAction(
                                        vertSlides.liftUp(),
                                        Path3.build()
                                ),
                                vertSlides.liftPlaceDown(),
                                outtakeClaw.openClaw(),
                                vertSlides.liftDown(),
                                outtakeArm.intakePos(),

                                Path4.build(),

                                outtakeClaw.closeClaw(),
                                new ParallelAction(
                                        vertSlides.liftUp(),
                                        outtakeArm.OuttakePos(),
                                        Path5.build()
                                ),
                                vertSlides.liftPlaceDown(),
                                outtakeClaw.openClaw(),
                                vertSlides.liftDown(),
                                outtakeArm.intakePos()


                                // Goes to Get the pre-placed sample

                        ),

                        horSlides.holdIn()
                )
        );


        while (opModeIsActive()) {}
    }
}
