package rab.local;

/******************************************************************************
 *  Compilation:  javac Pythagoras.java
 *  Execution:    java Pythagoras a b
 *  
 *  Given a and b, print out sqrt(a*a + b*b). Avoid overflow.
 *
 ******************************************************************************/

public class Formel {


   public static double Pythagoras (double x, double y) {
      double a = x;
      double b = y;
      double r = 0.0;

      if (Math.abs(a) > Math.abs(b)) {
         r = b / a;
         r = Math.abs(a) * Math.sqrt(1 + r*r);
      } else if (b != 0) {
         r = a / b;
         r = Math.abs(b) * Math.sqrt(1 + r*r);
      }
      return x;
   }
   
   /**
 * @param a Gegenkathete
 * @param b Ankathete
 * @param c Hypothenuse
 * @return Winkel Alpha
 */
public static double GetAngleAnyTriangle(double a, double b, double c) {
	   double finalAngle = 0.0;
	   
	   finalAngle = Math.acos(( Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c));
	   
	   return finalAngle;
   }
}
