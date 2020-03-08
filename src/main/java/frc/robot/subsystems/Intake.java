package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private Spark vertical = new Spark(Constants.INTAKE_MOVER);
  private Spark activator = new Spark(Constants.INTAKER_PWM);

  private DigitalInput[] intakeLimits = {new DigitalInput(Constants.INTAKE_LIMITS[0]), new DigitalInput(Constants.INTAKE_LIMITS[1])};

  public Intake() {
  }

  public void setVertical(boolean upDown) {
    boolean correspondingLimit = upDown ? intakeLimits[1].get() : intakeLimits[0].get();

    if(!correspondingLimit) {
      double speed = upDown ? 0.8 : -0.4;

      vertical.set(speed);
    } else {
      System.out.println("STOPPED");
      vertical.set(0);
    }

    // vertical.set(0.7);
  }

  public void setActivation(double speed) {
    activator.set(speed);
  }

  @Override
  public void periodic() {
    
  }
}
