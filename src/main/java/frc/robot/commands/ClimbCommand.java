package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.OperatorInput;

public class ClimbCommand extends CommandBase {
  // climb subsystem
  private Climb climb;

  // input subsystem
  private OperatorInput input;
  
  // initializes subsystems
  public ClimbCommand(Climb climber, OperatorInput operatorInput) {
    climb = climber;
    input = operatorInput;
  }

  @Override
  public void initialize() {
    
  }
  
  // takes input from controller, sets arm to corresponding speed
  @Override 
  public void execute() {
    double climbSpeed = input.tool.getBumper(Hand.kLeft) ? -0.75 : (input.tool.getBumper(Hand.kRight) ? 0.25 : 0);

    climb.setArm(climbSpeed);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
