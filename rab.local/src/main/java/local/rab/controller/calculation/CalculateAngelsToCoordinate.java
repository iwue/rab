package local.rab.controller.calculation;


import java.rmi.RemoteException;

import javafx.geometry.Point3D;
import local.rab.config.Statics;
import local.rab.devices.brick.BrickComponentHandler;

public class CalculateAngelsToCoordinate {
	private Point3D point3d = null;
	
	public Point3D calc(BrickComponentHandler componentHandler) {
		try {
			double angleTheta1 = componentHandler.getHingTheta1().getTachoCount() * Statics.getTransmissionTheta1();			
			double angleTheta2 = componentHandler.getHingTheta20().getTachoCount() * Statics.getTransmissionTheta2() * -1 + 90;
			double angleTheta3 = componentHandler.getHingTheta3().getTachoCount() * Statics.getTransmissionTheta3()* -1 - 90;
			double angleTheta4 = componentHandler.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4()* -1;
			
			return calc(angleTheta1, angleTheta2, angleTheta3, angleTheta4);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Point3D calc(double angleTheta1, double angleTheta2, double angleTheta3, double angleTheta4) {
		double z = Statics.getA1() 
				+ (Statics.getA2() * Math.sin(angleTheta2))
				+ (Statics.getA5() * Math.sin(angleTheta2 + angleTheta3));
		
		double c = (Statics.getA2() * Math.cos(angleTheta2))
				+ (Statics.getA5() * Math.cos(angleTheta2 + angleTheta3))
				+ Statics.getA6();
		
		double y = c * Math.sin(angleTheta1);
		double x = c * Math.cos(angleTheta1);
		
		point3d = new Point3D(x, y, z);
		return point3d;
	}
}