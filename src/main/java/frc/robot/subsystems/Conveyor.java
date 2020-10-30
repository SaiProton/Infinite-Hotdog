package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
  // sensors for detecting the ball position in the conveyor belt
  private DigitalInput[] ballSensors = new DigitalInput[2];

  // conveyor motor
  private CANSparkMax conveyor = new CANSparkMax(Constants.CONVEYOR_CAN, MotorType.kBrushless);

  // indexing wheel motor, for loading balls into launch position
  private CANSparkMax indexer = new CANSparkMax(Constants.INDEXER_CAN, MotorType.kBrushless);
  
  // constructor intitializes ball sensors
  public Conveyor() {
    for(int i = 0; i < 2; i++) {
      ballSensors[i] = new DigitalInput(Constants.CONVEYOR_OPTICALS[i]);
    }
  }

  // returns encoder value of the conveyor
  public double getEncoderVal() {
    return conveyor.getEncoder().getPosition();
  }

  // sets the encoder value manually, usually used to reset the counter
  public void setEncoder(double value) {
    conveyor.getEncoder().setPosition(value);
  }

  // checks if the conveyor is stuck
  public boolean checkStall() {
    CANEncoder encode = conveyor.getEncoder();
    return (encode.getVelocity() < 2 && conveyor.get() > 0.1);
  }

  // sets the conveyor speed
  public void setConveyor(double speed) {
    conveyor.set(speed);
  }

  // sets the indexer speed
  public void setIndexer(double speed) {
    indexer.set(speed);
  }

  // returns the sensor readings
  public boolean[] getBalls() {
    return new boolean[]{!ballSensors[0].get(), !ballSensors[1].get()};
  }

  // stops all motors in this subsystem
  public void cease() {
    conveyor.set(0);
    indexer.set(0);
  }

  @Override
  public void periodic() {
  }
}
