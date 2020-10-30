package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer roboContainer;

  // this calls when the robot code starts
  @Override
  public void robotInit() {
    roboContainer = new RobotContainer();
  }

  // always called periodically
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  // called when robot is disabled
  @Override
  public void disabledInit() {
  }

  // called continously (periodically) when robot is disabled
  @Override
  public void disabledPeriodic() {
  }

  // called when autonomous mode is initiated
  @Override
  public void autonomousInit() {
  }

  // called periodically in autonomous mode
  @Override
  public void autonomousPeriodic() {
  }

  // called when tele-operated mode is initiated
  @Override
  public void teleopInit() {
    // stores all commands in defaultCommands
    Command[] defaultCommands = roboContainer.getAllDefaultCommands();
    
    // loops through all commands, schedules them to run
    for(Command c : defaultCommands) {
      c.schedule(false);
    }
  }

  // calls continously when tele-operated mode is on
  @Override
  public void teleopPeriodic() {
  }

  // calls when test mode is initiated
  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  // calls periodically when test mode is on
  @Override
  public void testPeriodic() {
  }
}
