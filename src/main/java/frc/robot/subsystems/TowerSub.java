package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TowerSub extends SubsystemBase{
    private final PWMSparkMax towerMotor;

    public TowerSub(){
        towerMotor = new PWMSparkMax(Constants.towerMotor);
    }

    public void extend(){
        towerMotor.setVoltage(Constants.towerMotorVolt);
    }

    public void retract(){
        towerMotor.setVoltage(-Constants.towerMotorVolt);
    }
    public void stop(){
        towerMotor.setVoltage(0);
    }
}
