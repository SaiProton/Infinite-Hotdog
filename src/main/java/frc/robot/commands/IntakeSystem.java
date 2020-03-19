package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OperatorInput;

public class IntakeSystem extends CommandBase {
  private Intake intake;
  private OperatorInput input;

  private boolean inputUp = true;
  
  public IntakeSystem(Intake intaker, OperatorInput operatorInput) {
    intake = intaker;
    input = operatorInput;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double activate = input.tool.getTriggerAxis(Hand.kLeft) > 0.5 ? -0.75 : 0;
    activate = input.tool.getAButton() ? 0.75 : activate;
    inputUp = input.tool.getBButtonPressed() ? !inputUp : inputUp;
    
    intake.setVertical(inputUp);
    intake.setActivation(activate);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
