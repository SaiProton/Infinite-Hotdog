package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.OperatorInput;
import frc.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ConveyorComplex extends CommandBase {
  private Conveyor conveyor;
  private Turret turret;
  private OperatorInput input;
  private Intake intake;

  public static double conveyorSpeed = 0.25;
  private double indexerSpeed = 0.25;

  private final double ballMoveDist = (42.0 / (2.0 * Math.PI));

  private Timer delay = new Timer();
  private final double ballDelay = 7.0;

  public ConveyorComplex(Conveyor conveyorBelt, Turret theTurret, OperatorInput operatorInput, Intake intaker) {
    conveyor = conveyorBelt;
    turret = theTurret;
    input = operatorInput;
    intake = intaker;

    conveyor.setEncoder(0);
    delay.reset();
    delay.start();
  }

  @Override
  public void initialize() {
  }

  /*
   * 1. If ball is in the intake detector, then the conveyor is ON only if the
   * indexer has no ball. 2. If the indexer has a ball, but the shooter does not,
   * run the indexer motor and conveyor until the shooter has a ball.
   */

  @Override
  public void execute() {
    // if(input.tool.getAButton()) {
    // boolean[] sensorReadings = conveyor.getBalls();
    // // 0 = The intake detector
    // // 1 = The Indexer detector
    // boolean intake_detect = sensorReadings[0];
    // boolean indexer_detect = sensorReadings[1];
    // boolean turret_has_ball = turret.getBall();

    // for(int i = 0; i < sensorReadings.length; i++) {
    // SmartDashboard.putBoolean("sensor" + i, sensorReadings[i]);
    // }

    // // if the turret has no ball but the indexer can load one, lets move
    // everything up
    // if(indexer_detect == true && turret_has_ball == false) {
    // conveyor.setConveyor(0.4);
    // conveyor.setIndexer(0.6);
    // // turrets loaded, everything stop
    // } else if(turret_has_ball == true) {
    // if(indexer_detect == true)
    // conveyor.setConveyor(0); // only stop the conveyor if a ball is ready to be
    // indexxed
    // conveyor.setIndexer(0);
    // }

    // // if the intake can get a ball and the indexer has no ball then run
    // if(intake_detect == true) {
    // conveyor.setConveyor(0.4);
    // // if(indexer_detect == true) {
    // conveyor.setIndexer(0.6);
    // //}
    // } else { // otherwise turn off the conveyor
    // conveyor.setConveyor(0);
    // if(indexer_detect == false) {
    // conveyor.setIndexer(0);
    // }
    // }
    // } else {
    // conveyor.cease();
    // turret.cease();
    // }

    // if (!LimeBall.shooting) {
    //   if (sensorReadings[1] && !turret.getBall()) {
    //     delay.reset();
    //     conveyor.setConveyor(conveyorSpeed * 1.5);

    //     if(!turret.getBall()) {
    //       conveyor.setIndexer(conveyorSpeed);
    //     } else {
    //       conveyor.setIndexer(0);
    //     }
    //   } else if(delay.get() > ballDelay) {
    //     conveyor.setConveyor(0);
    //     conveyor.setIndexer(0);
    //   } 
    // }

    boolean[] sensorReadings = conveyor.getBalls();
    double encoderValue = conveyor.getEncoderVal();

    System.out.println("Encoder be like: " + encoderValue + ", bruh");
    SmartDashboard.putNumber("EncoderVal", encoderValue);

    if(!LimeBall.shooting) {
      if(sensorReadings[0]) {
        //conveyor.setEncoder(0);
        delay.reset();
        delay.start();
        conveyor.setConveyor(conveyorSpeed * 1.5);
        
        // double indexerSpeed = turret.getBall() ? 0 : conveyorSpeed;
        // conveyor.setIndexer(indexerSpeed);
      } else if(/*encoderValue > ballMoveDist*/ delay.get() > 0.07) {
        conveyor.setConveyor(0);
      }
    }

    if(sensorReadings[1] && !turret.getBall()) {
      conveyor.setIndexer(conveyorSpeed);
    }

    if(turret.getBall()) {
      conveyor.setIndexer(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    conveyor.cease();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
