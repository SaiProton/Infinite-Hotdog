package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OperatorInput extends SubsystemBase {
  public XboxController driver;
  public XboxController tool;

  public OperatorInput() {
    driver = new XboxController(Constants.DRIVER_CONTROLLER);
    tool = new XboxController(Constants.TOOL_CONTROLLER);
  }

  @Override
  public void periodic() {
    //add "just pressed" code for buttons here for toggling effects
  }
}
