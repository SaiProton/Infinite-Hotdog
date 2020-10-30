package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.OperatorInput;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeBall extends CommandBase {
  // dependant subsystems
  private OperatorInput input;
  private Turret turret;
  private Limelight lime;
  private Conveyor conveyor;

  // booleans for checking shooting status
  private boolean tracking = false;
  public static boolean shooting = false;
  private boolean lastShoot = false;
  private boolean lastSense = false;

  // various shooting delays
  private Timer delay = new Timer();
  private Timer feedDelay = new Timer();
  private Timer shootDelay = new Timer();

  // initializing subsystems
  public LimeBall(OperatorInput operatorInput, Turret theTurret, Conveyor conveyorBelt, Limelight limeLight) {
    input = operatorInput;
    turret = theTurret;
    lime = limeLight;
    conveyor = conveyorBelt;    
  }

  // turns on limelight LEDs, starts delays
  @Override
  public void initialize() {
    lime.setLEDs(true);
    feedDelay.start();
    shootDelay.start();
  }

  @Override
  public void execute() {
    // tracking = input.tool.getXButtonPressed() ? !tracking : tracking;
    // gets user input for shooting
    shooting = input.tool.getTriggerAxis(Hand.kRight) > 0.5 ? true : false;
    
    if(shooting) {
      // auto-tracking is set to false once actually shooting
      tracking = false;
      
      // sets turret to speed from 0-1
      turret.setShooters(0.5);

      if(!lastShoot) {
        delay.reset();
        delay.start();
      }
      
      // if the ball isnt in the shoot position
      if(!turret.getBall()) {
        // sets conveyor on
        conveyor.setConveyor(ConveyorComplex.conveyorSpeed * 1.5);
        if(shootDelay.get() > 0.15) {
          turret.setFeeder(0);
          feedDelay.reset();
          feedDelay.start();
        }
      } else if(delay.get() > 3 && feedDelay.get() > 0.3) {
        turret.setFeeder(0.85);
        shootDelay.reset();
        shootDelay.start();
      }
    } else {
      turret.cease();
    }

    // Y and A correspong to rotating the turret, (but its best to let it rotate manually)
    if(input.tool.getYButton()) {
      turret.setRotation(-0.9);
    } else if(input.tool.getAButton()) {
      turret.setRotation(0.9);
    } else {
      turret.setRotation(0);
    }

    lime.setLEDs(tracking);

    // if tracking mode is on
    if(tracking) {
      double xangle = lime.getValues().get("xangle");
      // System.out.println("AREA: " + lime.getValues().get("area"));
      // System.out.println("distance: " + 128.8 / Math.sqrt(lime.getValues().get("area")));

      turret.setRotation(-xangle / 5);
    }
    
    lastShoot = shooting;
    lastSense = turret.getBall();
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