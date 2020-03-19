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
  private OperatorInput input;
  private Turret turret;
  private Limelight lime;
  private Conveyor conveyor;

  private boolean tracking = false;
  public static boolean shooting = false;
  private boolean lastShoot = false;
  private boolean lastSense = false;

  private Timer delay = new Timer();
  private Timer feedDelay = new Timer();
  private Timer shootDelay = new Timer();

  public LimeBall(OperatorInput operatorInput, Turret theTurret, Conveyor conveyorBelt, Limelight limeLight) {
    input = operatorInput;
    turret = theTurret;
    lime = limeLight;
    conveyor = conveyorBelt;    
  }

  @Override
  public void initialize() {
    lime.setLEDs(true);
    feedDelay.start();
    shootDelay.start();
  }

  @Override
  public void execute() {
    tracking = input.tool.getXButtonPressed() ? !tracking : tracking;
    shooting = input.tool.getTriggerAxis(Hand.kRight) > 0.5 ? true : false;
    
    if(shooting) {
      tracking = false;
      
      turret.setShooters(1);

      if(!lastShoot) {
        delay.reset();
        delay.start();
      }

      if(!turret.getBall()) {
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

    // if(input.tool.getXButton()) {
    //   turret.setRotation(-0.5);
    // } else if(input.tool.getYButton()) {
    //   turret.setRotation(0.5);
    // }

    lime.setLEDs(tracking);

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