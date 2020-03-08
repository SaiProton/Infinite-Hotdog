package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistance extends CommandBase {
  private double inchesPerRotation = 6 * Math.PI;
  private double ticksToMove;

  public MoveDistance(double inches) {
    
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
