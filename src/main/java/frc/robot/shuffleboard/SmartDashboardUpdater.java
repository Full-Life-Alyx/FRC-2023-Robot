package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.autonomous.AutonomousPhaseType;
import edu.wpi.first.math.Pair;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SPI;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

public class SmartDashboardUpdater {
    private long lastTriggered;
    private HashMap<Spark, Pair<GenericEntry, String>> motors;
    private HashMap<Spark, String> driveMotors;
    private final SendableChooser<AutonomousPhaseType> chooser = new SendableChooser<>();
    AHRS gyro;
    private ShuffleboardTab tab = Shuffleboard.getTab("SmartDashboard");
    private GenericEntry gyroPitch;
    private NetworkTable limelight;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;

    public SmartDashboardUpdater(HashMap<Integer, String> motors, HashMap<Integer, String> driveMotors) {
        //Variables init only
        lastTriggered = System.currentTimeMillis();

        SparkMotorManager sparkMotorManager = SparkMotorManager.getInstance();
        SparkMotorManager driveSparkMotorManager = SparkMotorManager.getInstance();

        HashMap<Spark, Pair<GenericEntry, String>> sparkMotors = new HashMap<>();
        for (Integer port : motors.keySet()) {
            sparkMotors.put(sparkMotorManager.getMotor(port), Pair.<GenericEntry, String>of(null, motors.get(port)));
        }
        this.motors = sparkMotors;

        HashMap<Spark, String> driveSparkMotors = new HashMap<>();
        for (Integer port : driveMotors.keySet()) {
            driveSparkMotors.put(driveSparkMotorManager.getMotor(port), motors.get(port));
        }
        this.driveMotors = driveSparkMotors;

        gyro = new AHRS(SPI.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */

        //limelight = NetworkTableInstance.getDefault().getTable("limelight");

        //logic
        init();
    }

    public void init() {

        gyro.reset();

        //adding widgets to shuffleboard
        gyroPitch = tab.add("gyroPitch", gyro.getRoll())
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        tab.add(gyro)
        .withWidget(BuiltInWidgets.kGyro);
        for (Spark motor : motors.keySet()) {
            GenericEntry extraMotor = tab.add(motors.get(motor).getSecond() + " voltage", motor.get())
            .withWidget(BuiltInWidgets.kVoltageView)
            .withProperties(Map.of("min", -12, "max", 12))
            .getEntry();

            motors.replace(motor, new Pair(extraMotor, motors.get(motor).getSecond()));
        }
        //ledMode = limelight.setEntry(2);
        //ta = tab.add("ta", limelight.getEntry("ta"));



        /*Shuffleboard.getTab("SmartDashboard")
        .add("front left voltage", 1)
        .withWidget(BuiltInWidgets.kMecanumDrive)
        .getEntry();*/
        
        for (AutonomousPhaseType type : AutonomousPhaseType.values()) {
            //TODO un-hardcode this
            if (type == AutonomousPhaseType.DEFAULT) {
                chooser.addOption("default", type);
                continue;
            }
            chooser.addOption(type.name(), type);
        }
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        NetworkTable table = instance.getTable("Shuffleboard").getSubTable("MecanumDrive");
// set the values for the Mecanum Drive widget
        //Chat GPT test
        table.getEntry("frontLeftSpeed").setDouble(0.1);
        table.getEntry("frontRightSpeed").setDouble(0.2);
        table.getEntry("rearLeftSpeed").setDouble(0.3);
        table.getEntry("rearRightSpeed").setDouble(0.4);
        
    }

    public void update() {
        updateMotors();
        updateDebug();
        updateGyro();
        //updateLimelight();
    }

    private void updateMotors() {

        for(Spark motor : motors.keySet()) {
            motors.get(motor).getFirst().setDouble(motor.get());
        }


        /*for (Spark motor : motors.keySet()) {
            Shuffleboard.getTab("SmartDashboard")
            .add(motors.get(motor) + " voltage", motor.get())
            .withWidget(BuiltInWidgets.kVoltageView)
            .withProperties(Map.of("min", -1, "max", 1));
            /*SmartDashboard.putNumber(
                    motors.get(motor) + " voltage",
                    motor.get()
            );
        }*/
        
        /*for (Spark motor : driveMotors.keySet()) {
            Shuffleboard.getTab("SmartDashboard")
            .add(motors.get(motor) + " voltage", motor.get())
            .withWidget(BuiltInWidgets.kMecanumDrive)
            .withProperties(Map.of("min", -1, "max", 1))
            .getEntry();
            /*SmartDashboard.putNumber(
                    motors.get(motor) + " voltage",
                    motor.get()
            );
        }*/
    }

    private void updateGyro(){
        gyroPitch.setFloat(gyro.getRoll());
    }

    private void updateDebug() {
        SmartDashboard.putNumber("SystemTime", System.currentTimeMillis());
        SmartDashboard.putNumber("TriggerDelay", lastTriggered - System.currentTimeMillis());
        lastTriggered = System.currentTimeMillis();
    }

    private void updateLimelight(){
        ta = limelight.getEntry("ta");
        tx = limelight.getEntry("tx");
        ty = limelight.getEntry("ty");
    }

    public AutonomousPhaseType getChosen() {
        return chooser.getSelected();
    }

}