package frc.robot.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Projectiles {

    // Conversions
    public static double toMeters(double inch) {
        return inch/39.37;
    }

    // Constants:
    private static double ball_size = toMeters(7); // inch to m
    //private static double ball_weight = 0.02835; // kg

    private static double gearRatio = 2; // driver gear/driven gear
    private static double wheelDiameter = toMeters(4); //in meters; (4" over 1 meter)
    //private static double wheelWeight = 0.67; // kg
    private static double shooterCenterHeight = toMeters(24); // from the ground to the center of the exit angle

    private static double shooterAngle = Math.toRadians(50.0); // degrees (converted to rad));

    private static double g = 9.8; // m/sec^2 (GRAVITY)

    public static enum Motor {
        NEO(5778),
        m_775Pro(18700),
        CIM(5330),
        MiniCIM(5840),
        Bag(13180);

        public final int value;

        Motor(int value) {
          this.value = value;
        }
    }

    public static class Target {
        public static double heightToCenter = toMeters(98.25);// height from the floor to the center of target
        public static double dimensionY = toMeters(34/2); // offset Y distance from center
        public static double dimensionX = toMeters(34/2); //  offset Y distance from center

        /**
         *
         * @param height your projected shot height from the projectile function of time
         * @return true if height is within the bounding box of the target
         */
        public static boolean inRange(double height) {
            // check upper bound:
            if(heightToCenter + dimensionY > height + ball_size/2) {
                // check lower bound:
                if(heightToCenter - dimensionY < height - ball_size/2)
                    return true;
            }
            // does not meet criteria
            return false;
        }
    }

    /**
     * Calculates shot initial velocity for required distance knowing the target height
     * @param x_dist The lime light distance of your target
     * @return the initial linear velocity that is required for the output shaft
     */

    public static double getRequiredShotVelocity(double x_dist) {
       // Calculates linear velocity (in m/s) of required launch velocity
    	// vi = 0.5*csc(0) * sqrt(x^2 * 2g * tan^2(0) / (x * tan(0) - y)
    	double dY = (Target.heightToCenter - shooterCenterHeight);
        double vi = (0.5* (1/Math.sin(shooterAngle)) ) * Math.sqrt((
        			( Math.pow(x_dist, 2) * (2*g) * ( Math.pow(Math.tan(shooterAngle),2) ))
        			/ ( (x_dist * Math.tan(shooterAngle)) - dY)
        			));
        return vi;
    }

    /**
     *
     * @param initial vi grabbed from getRequiredShotVelocity (m/s)
     * @param max_rpm the motor you are using
     * @return angular velocity in RPM for the required output shaft
     */
    public static double getAngularVelocity(double initial, Motor motor) {
        // linear velocity: w = (vi/r) rev/s
        // Wrpm = w*60s
        double w = initial/(wheelDiameter/2)*60;

        // minimum required motor speed for the shot divided by gear ratio
        return Math.min(w, motor.value*gearRatio)/2;
    }

    private static double getAngularVelocity(double initial) {
        // linear velocity: w = (vi/r) rev/s
        // Wrpm = w*60s
        double w = initial/(wheelDiameter/2)*60;

        // minimum required motor speed for the shot divided by gear ratio
        return w/2;
    }

    public static double getFlightTime(double dist_to_target, double initial_v) {
        // x = vi*cos(0)*t;
        // t = x/vi*cos(0);
        return dist_to_target/(initial_v*Math.cos(shooterAngle));
    }

    public static double getProjectileHeight(double time, double initial_v) {
    	// y = vi*sin(0)*t - 0.5*g(t^2);
    	return shooterCenterHeight + (initial_v*Math.sin(shooterAngle))*time - 0.5*g*Math.pow(time, 2);
    }

    /**
     * Determines whether or not the shot will go in
     * @param dist_to_target The limielights detected distance to the target in meters
     * @param shooter_motor The motor you are using to shoot the ball
     * @return Is the shot going to score?
     */
    public static boolean inRange(double dist_to_target, Motor shooter_motor) {
        double vi = getRequiredShotVelocity(dist_to_target);
        // get the time from the distance:
        double flight_time = getFlightTime(dist_to_target, vi);
        // y = vi*sin(0)*t - 0.5*g(t^2);
        double heightOf = getProjectileHeight(flight_time, vi);
        // double check the shooter motor can do it:
        double w = vi/(wheelDiameter/2)*60;

        SmartDashboard.putNumber("Omega Val", w);
        // return will it hit?
        return /*Target.inRange(heightOf) &&*/ w <= shooter_motor.value*2;
    }
}
