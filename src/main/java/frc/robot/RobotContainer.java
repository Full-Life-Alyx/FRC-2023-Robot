// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveJoystickCartesian;
import frc.robot.commands.arm.ExtendArm;
import frc.robot.commands.arm.RetractArm;
import frc.robot.commands.arm.StopArm;
import frc.robot.commands.autonomous.AutonomousPhaseType;
import frc.robot.commands.autonomous.commandgroup.AutonomousChargeStation;
import frc.robot.commands.autonomous.commandgroup.AutonomousLeaveCommunity;
import frc.robot.commands.autonomous.commandgroup.AutonomousScore;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.claw.ClawOpen;
import frc.robot.commands.claw.ClawStop;
import frc.robot.commands.tower.DropTower;
import frc.robot.commands.tower.LiftTower;
import frc.robot.commands.tower.StopTower;
import frc.robot.subsystems.ArmSub;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.TowerSub;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveTrainSub driveSub = new DriveTrainSub();
    private final ClawSub clawSub = new ClawSub();
    private final ArmSub armSub = new ArmSub();
    private final TowerSub towerSub = new TowerSub();
    private final Robot robot;
    private final AHRS gyro;
    Joystick flightStickControl = new Joystick(1);
    Joystick flightStickDrive = new Joystick(0);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(Robot robot, AHRS gyro) {
        this.robot = robot;
        this.gyro = gyro;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    public void configureButtonBindings() {
        final JoystickButton butt7 = new JoystickButton(flightStickControl, 7);
        final JoystickButton butt8 = new JoystickButton(flightStickControl, 8);
        final JoystickButton butt9 = new JoystickButton(flightStickControl, 9);
        final JoystickButton butt10 = new JoystickButton(flightStickControl, 10);
        final JoystickButton butt11 = new JoystickButton(flightStickControl, 11);
        final JoystickButton butt12 = new JoystickButton(flightStickControl, 12);
        butt8.onTrue(new ClawOpen(clawSub));
        butt8.onFalse(new ClawStop(clawSub));

        butt7.onTrue(new ClawClose(clawSub));
        butt7.onFalse(new ClawStop(clawSub));

        butt10.onTrue(new ExtendArm(armSub));
        butt10.onFalse(new StopArm(armSub));

        butt9.onTrue(new RetractArm(armSub));
        butt9.onFalse(new StopArm(armSub));

        butt12.onTrue(new LiftTower(towerSub));
        butt12.onFalse(new StopTower(towerSub));

        butt11.onTrue(new DropTower(towerSub));
        butt11.onFalse(new StopTower(towerSub));

        driveSub.setDefaultCommand(
//            new RunCommand(
//                () -> driveSub.mecanumDrive(
//                    flightStick.getY(), flightStick.getX(), flightStick.getZ()), driveSub
//                )
//            );
                 /*new DriveCartesian(
                         driveSub,
                         flightStick.getRawAxis(1), //y speed - forwards & backwards
                         flightStick.getRawAxis(0), //x speed - strafe
                         flightStick.getRawAxis(2)  //z rotation - turning
                 )*/
                new DriveJoystickCartesian(
                        driveSub,
                        flightStickDrive
                )
        );
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand(AutonomousPhaseType chosen) {
        // return new AutonomousChargeStation(driveSub, gyro, robot);
        return new AutonomousLeaveCommunity(clawSub, towerSub, armSub, driveSub);                                                                            
        /*if (chosen == null) return null;
        switch (chosen) {
            case CHARGE_STATION: {
                return new AutonomousChargeStation(armSub, driveSub, gyro, robot);
            }
            case LEAVE_COMMUNITY: {
                return new AutonomousLeaveCommunity(armSub, driveSub);
            }
            case SCORE: {
                return new AutonomousScore(towerSub, armSub, clawSub, driveSub);
            }
            default: {
                System.out.println("Please select autonomous, continuing with default auto");
                return new AutonomousLeaveCommunity(armSub, driveSub);
            }
        }*/

    }
}
