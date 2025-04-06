package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WORLDSHARDWARE {
    DcMotorEx FL, FR, BL, BR, vertSlideLeft, vertSlideRight, horSlide;
    ColorSensor colorSensor;
    Servo intakeHor, intakeVert, intakeWrist, intakeClaw, outtakeArmLeft, outtakeArmRight, outtakeArmWrist, outtakeClaw;


    public WORLDSHARDWARE(HardwareMap hardwareMap) {
        //TODO: Drivetrain Motors
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FL.setDirection(DcMotorSimple.Direction.REVERSE);

        FR = hardwareMap.get(DcMotorEx.class, "FR");
        FR.setDirection(DcMotorSimple.Direction.FORWARD);

        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        BR = hardwareMap.get(DcMotorEx.class, "BR");
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");




        //TODO: Slide Motors
        vertSlideLeft = hardwareMap.get(DcMotorEx.class, "vertSlideLeft");
        vertSlideLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        vertSlideRight = hardwareMap.get(DcMotorEx.class, "vertSlideRight");
        vertSlideRight.setDirection(DcMotorSimple.Direction.FORWARD);


        horSlide = hardwareMap.get(DcMotorEx.class, "horizontal");
        horSlide.setDirection(DcMotorSimple.Direction.FORWARD);


        //TODO: Intake Servos
        intakeHor = hardwareMap.get(Servo.class, "firstServo");
        intakeHor.setDirection(Servo.Direction.FORWARD);

        intakeVert = hardwareMap.get(Servo.class, "secondServo");
        intakeVert.setDirection(Servo.Direction.FORWARD);

        intakeWrist = hardwareMap.get(Servo.class, "thirdServo");
        intakeWrist.setDirection(Servo.Direction.FORWARD);

        intakeClaw = hardwareMap.get(Servo.class, "fourthServo");
        intakeClaw.setDirection(Servo.Direction.FORWARD);

        //TODO: Outtake Servos

        outtakeArmLeft = hardwareMap.get(Servo.class, "rotateLeft");
        outtakeArmLeft.setDirection(Servo.Direction.REVERSE);

        outtakeArmRight = hardwareMap.get(Servo.class, "rotateRight");
        outtakeArmRight.setDirection(Servo.Direction.FORWARD);

        outtakeArmWrist = hardwareMap.get(Servo.class, "rotateClaw");
        outtakeArmWrist.setDirection(Servo.Direction.FORWARD);

        outtakeClaw = hardwareMap.get(Servo.class, "OuttakeClaw");
        outtakeClaw.setDirection(Servo.Direction.REVERSE);



    }
}
