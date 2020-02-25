package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.OperatorInput;

public class TankDrive extends CommandBase {
  private DriveTrain drive;
  private OperatorInput input;

  private double[] speedAdj = {0, 0};
  
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
    double rx = input.driver.getRawAxis(Constants.R_STICK_X);

    //for trigger speed changes:
    //Constants.MAX_SPEED = Constants.DEFAULT_SPD + (input.driver.getRawAxis(Constants.R_TRIGGER) - input.driver.getRawAxis(Constants.L_TRIGGER)) / 4;

    Constants.MAX_SPEED = Constants.DEFAULT_SPD + ((input.driver.getRawButton(Constants.R_BUMPER) ? 1 : 0) - (input.driver.getRawButton(Constants.L_BUMPER) ? 1 : 0)) / 4;
    Constants.MAX_SPEED += input.driver.getRawButton(Constants.X_BUTTON) ? (1 - Constants.MAX_SPEED) : 0;

    double lSpeed = Constants.MAX_SPEED * (ly - rx);
    double rSpeed = Constants.MAX_SPEED * (ly + rx);

    drive.setMotors(lSpeed + speedAdj[0], rSpeed + speedAdj[1]);
  }

  private void alignBase() {
    //math is trash, fix later
    double yaw = drive.navX.getYaw();

    speedAdj[0] = (yaw + 180) / 180;
    speedAdj[1] = -speedAdj[0];
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
