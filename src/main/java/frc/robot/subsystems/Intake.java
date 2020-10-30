package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  // motor responsible for moving the intake up and down
  private Spark vertical = new Spark(Constants.INTAKE_MOVER);
  
  // motor that activates the intake roller mechanism
  private Spark activator = new Spark(Constants.INTAKER_PWM);

  // limit switches preventing the intake from moving too far up/down
  private DigitalInput[] intakeLimits = {
    new DigitalInput(Constants.INTAKE_LIMITS[0]), 
    new DigitalInput(Constants.INTAKE_LIMITS[1])
  };

  public Intake() {
  }

  // sets the vertical value (either up/down)
  public void setVertical(boolean upDown) {
    boolean correspondingLimit = upDown ? intakeLimits[1].get() : intakeLimits[0].get();

    if(!correspondingLimit) {
      double speed = upDown ? 1 : -0.4;

      vertical.set(speed);
    } else {
      System.out.println("STOPPED");
      vertical.set(0);
    }

    // vertical.set(0.7);
  }

  // sets the speed of the intake roller
  public void setActivation(double speed) {
    activator.set(speed);
  }

  @Override
  public void periodic() {
    
  }
}
