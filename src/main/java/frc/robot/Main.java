
package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

//Dont mess with Main or main(), bruh
public final class Main {
  private Main() {
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
