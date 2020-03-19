package frc.robot.commands;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.OperatorInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.utils.PIDControl;
import frc.robot.utils.Projectiles;
import frc.robot.utils.Projectiles.Motor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class ShootCommand extends CommandBase {

    private PIDControl flywheel_ctrl;
    private Limelight lime;
    private Turret turret;
    private OperatorInput input;

    private boolean ramp_up = false;

    public ShootCommand(Turret turretSystem, Limelight limelight, OperatorInput userInput) {
      turret = turretSystem;
      lime = limelight;
      input = userInput;
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      // pid loop setup: if system oscillates too much tune kp down
      flywheel_ctrl = new PIDControl(0.005, 0.000001, 0);
      // set output limits to defer brownouts
      flywheel_ctrl.setOutputLimits(-0.95, 0.95);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      SmartDashboard.putNumber("DIST", 128.8 / Math.sqrt(lime.getValues().get("area")));
      SmartDashboard.putBoolean("InRange", Projectiles.inRange(128.8 / Math.sqrt(lime.getValues().get("area")), Motor.NEO));
      //meant to be run while pressed:
      boolean button = input.tool.getTriggerAxis(Hand.kRight) > 0.5 ? true : false;
      if(button == true) {
        if(/*lime.hasTargets() && */ramp_up == false) {
          double distance = 128.8 / Math.sqrt(lime.getValues().get("area"));
          boolean isInRange = Projectiles.inRange(distance, Motor.NEO);
          if(isInRange == true) {
            double shotVelocity = Projectiles.getRequiredShotVelocity(distance);

            PrepareToFire(Projectiles.getAngularVelocity(shotVelocity, Motor.NEO));
          }
        }
      } else {
        ramp_up = false;
      }

  }

  // i caution you to be very careful with this
  public void PrepareToFire(double target_rpm) {
      if(ramp_up == false) {
        ramp_up = true;
        flywheel_ctrl.setSetpoint(target_rpm);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(ramp_up == true) {
                  double current_rpm = turret.getRPM();
                  double kPower = flywheel_ctrl.getOutput(current_rpm);
                  turret.setShooters(kPower);
                }
            }
        }).run();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
