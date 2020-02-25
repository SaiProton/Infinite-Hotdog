package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.OperatorInput;
import frc.robot.subsystems.Turret;

public class LimeBall extends CommandBase {
  private OperatorInput input;
  private Turret turret;
  private Limelight lime;
  private Conveyor conveyor;

  private boolean tracking = false;
  private int ballAmount = 0;

  public LimeBall(OperatorInput operatorInput, Turret theTurret, Conveyor conveyorBelt, Limelight limeLight) {
    input = operatorInput;
    turret = theTurret;
    lime = limeLight;
    conveyor = conveyorBelt;

    addRequirements(input);
    addRequirements(turret);
    addRequirements(conveyor);
    addRequirements(lime);    
  }

  @Override
  public void initialize() {
    lime.setLEDs(false);
  }

  @Override
  public void execute() {
    tracking = input.tool.getBumperPressed(Hand.kLeft) ? !tracking : tracking;
    ballAmount = input.tool.getBumperPressed(Hand.kRight) ? 1 : (input.tool.getTriggerAxis(Hand.kRight) > 0.5 ? 5 : ballAmount);

    if(ballAmount > 0) {
      tracking = false;

      turret.setShooters(0.6);
      turret.setFeeder(0.8);

      if(input.tool.getBumperPressed(Hand.kRight)) {
        conveyor.cease();
      }
    } else {
      turret.cease();
    }

    lime.setLEDs(tracking);

    if(tracking) {
      double xangle = lime.getValues().get("xangle");
      
      //tweak denominator if too sensitive/insensitive
      turret.setRotation(xangle / 15);
    }

    ballAmount = 0;
  }

  @Override
  public void end(boolean interrupted) {
    conveyor.cease();
    turret.cease();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
