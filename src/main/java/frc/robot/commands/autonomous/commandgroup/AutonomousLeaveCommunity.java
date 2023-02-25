package frc.robot.commands.autonomous.commandgroup;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.StrafeDirection;
import frc.robot.commands.autonomous.AutoDrive;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.utils.CustomaryLength;
import frc.robot.utils.CustomaryLengthUnit;

public class AutonomousLeaveCommunity extends SequentialCommandGroup{
    public AutonomousLeaveCommunity(DriveTrainSub driveTrainSub, AHRS gyro, Robot robot) {
        addCommands(
                new AutoDrive(
                        driveTrainSub,
                        new CustomaryLength(5, CustomaryLengthUnit.FEET),
                        StrafeDirection.FORWARD
                )

        );

    }
}