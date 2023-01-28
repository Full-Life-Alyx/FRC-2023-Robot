package frc.robot.commands.tower;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TowerSub;

public class LiftTower extends CommandBase{
    private final TowerSub tower;

    public LiftTower(TowerSub subsystem){
        tower = subsystem;
        addRequirements(tower);
    }

    // only goes once at beginning when command is called
    @Override
    public void initialize(){
        
    }
    
    // keeps repeating until the command ends
    @Override
    public void execute(){
        tower.lift();
    }
  
    //only goes once at end when command is finishing
    @Override
    public void end(boolean interrupted){
    
    }
    
    //condition for the command to end on its own
    @Override
    public boolean isFinished(){

        return false;
    }
}