package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  private Spark[] shooters = new Spark[2];
  private Spark feeder = new Spark(Constants.FEEDER_PWM);
  private Spark rotator = new Spark(Constants.ROTATOR_PWM);
  private DigitalInput ballReadyDetector = new DigitalInput(2);
  
  public Turret() {
    for(int i = 0; i < 2; i++) {
      shooters[i] = new Spark(Constants.SHOOTER_PWMs[i]);
    }
  }

  public void setShooters(double speed) {
    for(int i = 0; i < 2; i++) {
      shooters[i].setSpeed(speed);
    }
  }

  public void setFeeder(double speed) {
    feeder.set(speed);
  }
  
  public void setRotation(double speed) { 
    //hopefully assuming that the limit switches automatically do their thing (please dont destroy the turret please)
    rotator.set(speed);
  }

  public boolean getBall() {
    return !ballReadyDetector.get();
  }

  public void cease() {
    rotator.set(0);
    feeder.set(0);
    setShooters(0);
  }

  @Override
  public void periodic() {
    //while encoder value is not close to angle it is supposed to be set, continue moving, else stop
  }
}
