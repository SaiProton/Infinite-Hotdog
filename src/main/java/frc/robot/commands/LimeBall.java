package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.OperatorInput;
import frc.robot.subsystems.Turret;

public class LimeBall extends CommandBase {
  private OperatorInput input;
  private Turret turret;
  // private Limelight lime;
  private Conveyor conveyor;

  private boolean tracking = false;
  public static boolean shooting = false;

  public LimeBall(OperatorInput operatorInput, Turret theTurret, Conveyor conveyorBelt/*, Limelight*/) {
    input = operatorInput;
    turret = theTurret;
    // lime = limeLight;
    conveyor = conveyorBelt;

    // addRequirements(input);
    // addRequirements(turret);
    // addRequirements(conveyor);
    // addRequirements(lime);    
  }

  @Override
  public void initialize() {
    // lime.setLEDs(false);
  }

  @Override
  public void execute() {
    tracking = input.tool.getBumperPressed(Hand.kLeft) ? !tracking : tracking;
    shooting = input.tool.getTriggerAxis(Hand.kRight) > 0.5 ? true : false;
    
    if(shooting) {
      tracking = false;
      // turret.setShooters(0.5);
      if(!turret.getBall()) {
        conveyor.setConveyor(ConveyorComplex.conveyorSpeed * 1.5);
      } else {
        turret.setFeeder(0.85);
      }
    } else {
      turret.cease();
    }

    if(input.tool.getXButton()) {
      turret.setRotation(-1);
    } else if(input.tool.getYButton()) {
      turret.setRotation(1);
    }

    // lime.setLEDs(tracking);

    if(tracking) {
      // double xangle = lime.getValues().get("xangle");
      
      //tweak denominator if too sensitive/insensitive
      // turret.setRotation(xangle / 15);
    }
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