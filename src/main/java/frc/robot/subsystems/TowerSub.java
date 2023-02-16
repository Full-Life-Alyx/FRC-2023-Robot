package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TowerSub extends SubsystemBase{
    private final CANSparkMax towerMotor;
    private RobotContainer container;

    public TowerSub(RobotContainer container){
        this.container = container;
        towerMotor = new CANSparkMax(Constants.towerMotor, MotorType.kBrushless);
        towerMotor.setIdleMode(IdleMode.kBrake);
    }
    

    public void lift(){
        towerMotor.setVoltage(-Constants.towerMotorVolt);
    }

    public void drop(){
        towerMotor.setVoltage(Constants.towerMotorVolt);
    }
    public void stop(){
        towerMotor.setVoltage(0);
    }
    public void towerControl(double speed){
        towerMotor.setVoltage(
                container.deadBand(
                        speed,
                        Constants.deadband
                ) * Constants.towerMotorVolt
        );

    }
}
