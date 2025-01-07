package frc.robot.util;

public class UqUtil {
    /**
     * Clamp `var` to 0 +/- `limit`
     * @param var
     * @param limit
     * @return
     */
    public static double clamp(double var, double limit) {
        if (var > limit) {
          var = limit;
        } else if (-var < -limit) {
          var = -limit;
        }

        return var;
    }
}
