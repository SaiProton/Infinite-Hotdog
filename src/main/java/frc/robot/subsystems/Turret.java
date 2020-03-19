package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends SubsystemBase {
  private CANSparkMax[] shooters = new CANSparkMax[2];
  private Spark feeder = new Spark(Constants.FEEDER_PWM);
  private Spark rotator = new Spark(Constants.ROTATOR_PWM);
  private DigitalInput ballReadyDetector = new DigitalInput(Constants.BALL_LAUNCH_OPTICAL);
  private CANEncoder shootEncoder;

  private long lastTime = System.nanoTime();
  private double lastRev;

  private double rpm = 0;
  
  public Turret() {
    for(int i = 0; i < 2; i++) {
      shooters[i] = new CANSparkMax(Constants.SHOOTER_CANS[i], MotorType.kBrushless);
    }

    shootEncoder = shooters[0].getEncoder();
    lastRev = shootEncoder.getPosition();
  }

  public void setShooters(double speed) {
    shooters[0].set(speed);
    shooters[1].set(-speed);
  }

  public void setFeeder(double speed) {
    feeder.set(speed);
  }
  
  public void setRotation(double speed) { 
    //add limit switch stuff here
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

  public double getRPM() {
    return rpm;
  }

  @Override
  public void periodic() {
    long currentTime = System.nanoTime();
    long deltaTime = currentTime - lastTime;

    double denominator = deltaTime / 6e14;

    double currentEncoder = shootEncoder.getPosition();

    SmartDashboard.putNumber("ENCODER", currentEncoder);
    // System.out.println("ENCODER: " + currentEncoder);

    double numerator = Math.abs(currentEncoder - lastRev) / 42;

    rpm = numerator / denominator;

    SmartDashboard.putNumber("RPM", rpm);
  }
}
