package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
  private DigitalInput[] ballSensors = new DigitalInput[2];
  private Spark conveyor = new Spark(0);
  private Spark indexer = new Spark(0);

  private int ballCount = 0;
  
  public Conveyor() {
    for(int i = 0; i < 2; i++) {
      ballSensors[i] = new DigitalInput(Constants.CONVEYOR_OPTICALS[i]);
    }
  }

  public void setConveyor(double speed) {
    conveyor.set(speed);
  }

  public void setIndexer(double speed) {
    indexer.set(speed);
  }

  public boolean[] getBalls() {
    return new boolean[]{!ballSensors[0].get(), !ballSensors[1].get()};
  }

  public void changeBalls(int amount) {
    ballCount += amount;
  }

  public int ballCount() {
    return ballCount;
  }

  public void cease() {
    conveyor.set(0);
    indexer.set(0);
  }

  @Override
  public void periodic() {
  }
}
