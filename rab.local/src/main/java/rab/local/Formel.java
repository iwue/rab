package rab.local;

public class Formel {


   public static double Pythagoras (double x, double y) {
      return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
   }
   
   /**
 * @param a Gegenkathete
 * @param b Ankathete
 * @param c Hypothenuse
 * @return Winkel Alpha
 */
public static double GetAngleAnyTriangle(double a, double b, double c) {
	   double finalAngle = 0.0;
	   
	   finalAngle = Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c));
	   
	   return Math.toDegrees(finalAngle);
   }
}
