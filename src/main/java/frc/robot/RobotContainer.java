package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

public class RobotContainer {
  private OperatorInput operatorInput = new OperatorInput();
  private DriveTrain driveTrain = new DriveTrain();
  private Conveyor conveyor = new Conveyor();
  private Intake intake = new Intake();
  private Limelight limeLight = new Limelight();
  private Turret turret = new Turret();
  private Climb climb = new Climb();

  private TankDrive tankDrive = new TankDrive(driveTrain, operatorInput);
  private LimeBall limeBall = new LimeBall(operatorInput, turret, conveyor , limeLight);
  private IntakeSystem intakeSystem = new IntakeSystem(intake, operatorInput);
  private ConveyorComplex conveyorComplex = new ConveyorComplex(conveyor, turret, operatorInput, intake);
  private ClimbCommand climbCommand = new ClimbCommand(climb, operatorInput);
  private ShootCommand shootCommand = new ShootCommand(turret, limeLight, operatorInput);

  public RobotContainer() {
  }

  public Command[] getAllDefaultCommands() {
    return new Command[] {tankDrive, limeBall, conveyorComplex, intakeSystem, climbCommand};
  }

  // public Command getAutonomousCommand() {
  //   return autoCommand;
  // }
}
