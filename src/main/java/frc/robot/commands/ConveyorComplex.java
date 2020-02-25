package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Turret;

public class ConveyorComplex extends CommandBase {
  private Conveyor conveyor;
  private Turret turret;

  private boolean lastFrontSense = false;

  public ConveyorComplex(Conveyor conveyorBelt, Turret theTurret) {
    conveyor = conveyorBelt;
    turret = theTurret;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(conveyor.getBalls()[0] != lastFrontSense) {
      conveyor.changeBalls(1);
    }

    while(conveyor.getBalls()[1] && !turret.getBall()) {
      conveyor.setIndexer(0.5);
    }

    conveyor.setIndexer(0);

    while(!conveyor.getBalls()[1] && conveyor.ballCount() > 0) {
      conveyor.setConveyor(0.5);
    }

    conveyor.setConveyor(0);

    lastFrontSense = conveyor.getBalls()[0];
  }

  @Override
  public void end(boolean interrupted) {
    conveyor.cease();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
