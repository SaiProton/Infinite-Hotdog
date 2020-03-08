package frc.robot.subsystems;

// import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private CANSparkMax[] leftMotors = new CANSparkMax[2];
  private CANSparkMax[] rightMotors = new CANSparkMax[2];
  // public AHRS navX = new AHRS(Port.kMXP);

  public DriveTrain() {
    for(int i = 0; i < 2; i++) {
      leftMotors[i] = new CANSparkMax(Constants.LEFT_DRIVE_CANS[i], MotorType.kBrushless);
      rightMotors[i] = new CANSparkMax(Constants.RIGHT_DRIVE_CANS[i], MotorType.kBrushless);
      rightMotors[i].setInverted(true);
    }
  }

  public void setMotors(double left, double right) {
    for(int i = 0; i < 2; i++) {
      leftMotors[i].set(left);
      rightMotors[i].set(right);
    }
  }

  @Override
  public void periodic() {
    //use for encoder ticking or other monitoring possibly
  }
}
