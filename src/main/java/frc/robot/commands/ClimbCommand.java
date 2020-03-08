package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.OperatorInput;

public class ClimbCommand extends CommandBase {
  private Climb climb;
  private OperatorInput input;
  
  public ClimbCommand(Climb climber, OperatorInput operatorInput) {
    climb = climber;
    input = operatorInput;
  }

  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() {
    double climbSpeed = input.tool.getBumper(Hand.kLeft) ? -1 : (input.tool.getBumper(Hand.kRight) ? 0.5 : 0);

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
