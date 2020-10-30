package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  // hashmap used for keeping track of limelight readings
  private HashMap<String, Double> limeValues = new HashMap<String, Double>();

  public Limelight() {

  }

  // gets limelight values needed, returns values
  public HashMap<String, Double> getValues() {
    limeValues.put("xangle", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
    limeValues.put("yangle", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    limeValues.put("area", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0));

    return limeValues;
  }

  // sets the leds of limelight on or off
  public void setLEDs(boolean ledMode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ledMode ? 0 : 1);
  }

  @Override
  public void periodic() {
    
  }
}
