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
  // dependant subsystems
  private Conveyor conveyor;
  private Turret turret;
  private OperatorInput input;
  private Intake intake;

  // conveyor and indexer speed values
  public static double conveyorSpeed = 0.25;
  private double indexerSpeed = 0.25;

  // theoretical amount the ball should move within the conveyor
  private final double ballMoveDist = (42.0 / (2.0 * Math.PI));

  // delay values for conveyor management
  private Timer delay = new Timer();
  private final double ballDelay = 0.07;

  // initializes dependant subsystems, starts delays
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
    // gets sensor readings as a list of booleans
    boolean[] sensorReadings = conveyor.getBalls();

    // gets encoder value as a decimal value
    double encoderValue = conveyor.getEncoderVal();

    // checks if not currently shooting
    if(!LimeBall.shooting) {
      // checks if the first sensor reads true
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

    // if the second sensor sees a ball, and the turret doesnt currently see a ball in the launch position, move the ball to the position
    if(sensorReadings[1] && !turret.getBall()) {
      conveyor.setIndexer(conveyorSpeed);
    }

    // if a ball is in the launch position, set indexer to 0
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
