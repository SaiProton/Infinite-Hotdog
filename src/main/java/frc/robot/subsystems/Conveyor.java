package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
  private DigitalInput[] ballSensors = new DigitalInput[2];
  private CANSparkMax conveyor = new CANSparkMax(Constants.CONVEYOR_CAN, MotorType.kBrushless);
  private CANSparkMax indexer = new CANSparkMax(Constants.INDEXER_CAN, MotorType.kBrushless);
  
  public Conveyor() {
    for(int i = 0; i < 2; i++) {
      ballSensors[i] = new DigitalInput(Constants.CONVEYOR_OPTICALS[i]);
    }
  }

  public double getEncoderVal() {
    return conveyor.getEncoder().getPosition();
  }

  public void setEncoder(double value) {
    conveyor.getEncoder().setPosition(value);
  }

  public boolean checkStall() {
    CANEncoder encode = conveyor.getEncoder();
    return (encode.getVelocity() < 2 && conveyor.get() > 0.1);
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

  public void cease() {
    conveyor.set(0);
    indexer.set(0);
  }

  @Override
  public void periodic() {
  }
}
