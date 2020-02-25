package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private Spark vertical = new Spark(0);
  private Spark activator = new Spark(0);

  public Intake() {

  }

  public void setVertical(boolean upDown) {
    vertical.set(upDown ? 1 : -1);
  }

  public void setActivation(double speed) {
    activator.set(speed);
  }

  @Override
  public void periodic() {
    
  }
}
