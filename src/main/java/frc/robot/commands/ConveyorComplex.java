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
  private final double ballDelay = 0.07;

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

  @Override
  public void execute() {
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
      } else if(/*encoderValue > ballMoveDist*/ delay.get() > ballDelay) {
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
