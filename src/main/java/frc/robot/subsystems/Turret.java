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
  // The two shooters used to shoot the ball (shocking)
  private CANSparkMax[] shooters = new CANSparkMax[2];

  // The rotator motor that feeds the ball into the output position
  private Spark feeder = new Spark(Constants.FEEDER_PWM);

  // The turret rotation motor
  private Spark rotator = new Spark(Constants.ROTATOR_PWM);

  //The sensor that detects if the output position is empty
  private DigitalInput ballReadyDetector = new DigitalInput(Constants.BALL_LAUNCH_OPTICAL);
  
  // --not currently used-- encoder used to measure the amount of rotations of the shooting motor
  private CANEncoder shootEncoder;

  private long lastTime = System.nanoTime();
  private double lastRev;

  // --unused again--, supposed to be used to calculate the shooting speed
  private double rpm = 0;
  
  public Turret() {
    // initializes the shooters
    for(int i = 0; i < 2; i++) {
      shooters[i] = new CANSparkMax(Constants.SHOOTER_CANS[i], MotorType.kBrushless);
    }

    shootEncoder = shooters[0].getEncoder();
    lastRev = shootEncoder.getPosition();
  }

  // sets shooter speed
  public void setShooters(double speed) {
    shooters[0].set(speed);
    shooters[1].set(-speed);
  }

  // sets the feeder motor speed
  public void setFeeder(double speed) {
    feeder.set(speed);
  }
  
  public void setRotation(double speed) { 
    //add limit switch stuff here
    rotator.set(speed);
  }

  // returns if a ball is in the output position
  public boolean getBall() {
    return !ballReadyDetector.get();
  }

  // sets every turret motor to 0 
  public void cease() {
    rotator.set(0);
    feeder.set(0);
    setShooters(0);
  }

  // --unused--, returns the rpm of the shooting motor
  public double getRPM() {
    return rpm;
  }

  // does periodic functions needed for this subsystem
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
