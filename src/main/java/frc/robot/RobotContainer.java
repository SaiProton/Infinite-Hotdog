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

  private TankDrive tankDrive = new TankDrive(driveTrain, operatorInput);
  private LimeBall limeBall = new LimeBall(operatorInput, turret, conveyor, limeLight);
  private IntakeSystem intakeSystem = new IntakeSystem(intake, operatorInput);
  private ConveyorComplex conveyorComplex = new ConveyorComplex(conveyor, turret);

  public RobotContainer() {
    //register subsystems that need to be registered here
  }

  public Command[] getAllDefaultCommands() {
    return new Command[] {tankDrive, limeBall, intakeSystem, conveyorComplex};
  }

  // public Command getAutonomousCommand() {
  //   return autoCommand;
  // }
}
