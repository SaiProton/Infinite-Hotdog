package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.OperatorInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive extends CommandBase {
  // subsystems for inputs and drive
  private DriveTrain drive;
  private OperatorInput input;

  // --not used cause buggy-- adjustments used for auto-tracking
  private double[] speedAdj = {0, 0};
  
  // sets local variables to passed in variables when instantiating
  public TankDrive(DriveTrain driveSystem, OperatorInput userInput) {
    drive = driveSystem;
    input = userInput;

    addRequirements(drive);
    addRequirements(input);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double ly = input.driver.getRawAxis(Constants.L_STICK_Y);
    double rx = -input.driver.getRawAxis(Constants.R_STICK_X) * 0.75;

    SmartDashboard.putNumber("LY", ly);
    SmartDashboard.putNumber("RX", rx);

    ly = Math.abs(ly) > 0.2 ? ly : 0;
    rx = Math.abs(rx) > 0.2 ? rx : 0;

    //for trigger speed changes:
    //Constants.MAX_SPEED = Constants.DEFAULT_SPD + (input.driver.getRawAxis(Constants.R_TRIGGER) - input.driver.getRawAxis(Constants.L_TRIGGER)) / 4;

    Constants.MAX_SPEED = Constants.DEFAULT_SPD + ((input.driver.getRawButton(Constants.R_BUMPER) ? 1 : 0) - (input.driver.getRawButton(Constants.L_BUMPER) ? 1 : 0)) / 4;
    Constants.MAX_SPEED += input.driver.getRawButton(Constants.X_BUTTON) ? (1 - Constants.MAX_SPEED) : 0;

    double lSpeed = Constants.MAX_SPEED * (ly - rx);
    double rSpeed = Constants.MAX_SPEED * (ly + rx);

    drive.setMotors(-lSpeed + speedAdj[0], -rSpeed + speedAdj[1]);
  }

  @Override
  public void end(boolean interrupted) {
    drive.setMotors(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
