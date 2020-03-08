package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private Spark climbArm = new Spark(Constants.CLIMB_ARM_PWM);
  private Spark climbRope = new Spark(Constants.CLIMB_ROPE_PWM);

  private DigitalInput[] climbLimits = {new DigitalInput(Constants.CLIMB_LIMITS[0]), new DigitalInput(Constants.CLIMB_LIMITS[1])};

  public Climb() {
    
  }

  public void setArm(double speed) {
    boolean downLimit = climbLimits[0].get();
    boolean upLimit = climbLimits[1].get();

    speed = (downLimit && speed < 0) ? 0 : speed;
    speed = (upLimit && speed > 0) ? 0 : speed;
    
    climbArm.set(speed);
  }

  public void setRope(double speed) {
    climbRope.set(speed);
  }

  @Override
  public void periodic() {

  }
}
