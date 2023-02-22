package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.StrafeDirection;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.utils.CustomaryLength;
import frc.robot.utils.CustomaryLengthUnit;

public class AutonomousPhase extends SequentialCommandGroup {
    public AutonomousPhase(DriveTrainSub driveTrainSub, AutonomousPhaseType type) {
        System.out.println("AUTO START");
        addRequirements(driveTrainSub);
        switch (type) {
            case DEFAULT: {
                System.out.println("DEFAULT");

                addCommands(
                        new AutoDrive(
                                driveTrainSub,
                                new CustomaryLength(2.6, CustomaryLengthUnit.FEET),
                                StrafeDirection.LEFT
                        ),
                        new WaitCommand(0),
                        new AutoDrive(
                                driveTrainSub,
                                new CustomaryLength(4.5, CustomaryLengthUnit.FEET),
                                StrafeDirection.FORWARD
                        ),
                        new WaitCommand(1)
                );
                break;

            }
            case ALTERNATIVE: {
                System.out.println("ALT");
                addCommands(
                        new AutoDrive(
                                driveTrainSub,
                                new CustomaryLength(12, CustomaryLengthUnit.FEET),
                                StrafeDirection.LEFT
                        ),
                        new WaitCommand(0),
                        new AutoDrive(
                                driveTrainSub,
                                new CustomaryLength(4.5, CustomaryLengthUnit.FEET),
                                StrafeDirection.FORWARD
                        ),
                        new WaitCommand(1)
                );
                break;
            }
            case CHARGE_STATION: {

            }
            default: {
                System.out.println("Bruh, none");
                break;
            }
        }
    }
}
