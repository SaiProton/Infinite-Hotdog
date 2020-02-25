package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OperatorInput;

public class IntakeSystem extends CommandBase {
  private Intake intake;
  private OperatorInput input;

  private boolean upDown = false;
  
  public IntakeSystem(Intake intaker, OperatorInput operatorInput) {
    intake = intaker;
    input = operatorInput;
    
    addRequirements(intake);
    addRequirements(input);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    upDown = input.driver.getAButtonPressed() ? !upDown : upDown;
    int activate = input.driver.getTriggerAxis(Hand.kLeft) > 0.5 ? 0 : 1;

    intake.setVertical(upDown);
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
