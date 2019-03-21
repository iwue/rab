package rab.local;

import javafx.geometry.Point3D;

public class Statics {
	
	private static String propertyFile = "rab.properties";
	// Länge der Achsen in mm
	private static double a1 = 260;
	private static double a2 = 168;
	private static double a5 = 162;
	private static double a6 = 140;
	
	private static int maxAcceleration		= 6000;
	
	private static String iPBrickLeft 			= "192.168.0.10";
	private static String iPBrickRight 		= "192.168.0.20";
	
	// Startposition des TCP
	private static double startX = 304;
	private static double startY = 0;
	private static double startZ = 428;
	
	private static Point3D currentPosition = new Point3D(0, 0 ,0); 
	
	private static double currentX;
	private static double currentY;
	private static double currentZ;
	
	private static double transmissionTheta1 = 0.1;
	private static double transmissionTheta2 = 0.1;
	private static double transmissionTheta3 = 0.1;
	private static double transmissionTheta4 = 0.33333333;
	
	private static double interval = 5;
	
	private static double maxSpeedOnCoordinateSystem = 2;
	private static double maxSpeedEffector = 20;
	
	private static double dualshockStopRange = 0.1;
	
	private static boolean theta4Automatic = true;
	
	// Pro Sekunde
	private static double maxSpeedMotor = 200;
	
	/*
	 * Mode 1 = Koordinaten System
	 * Mode 2 = Direkte Motoren steuerung
	 */
	private static int mode = 2;
	
	
	public static void initProperties() {
		a1 = Double.parseDouble(ConfigHandler.getProperty("a1"));
		a2 = Double.parseDouble(ConfigHandler.getProperty("a2"));
		a5 = Double.parseDouble(ConfigHandler.getProperty("a5"));
		a6 = Double.parseDouble(ConfigHandler.getProperty("a6"));
		
		maxAcceleration = Integer.parseInt(ConfigHandler.getProperty("maxAcceleration"));
		iPBrickLeft = ConfigHandler.getProperty("iPBrickLeft");
		iPBrickRight = ConfigHandler.getProperty("iPBrickRight");
		
		// Startposition des TCP
		startX = Double.parseDouble(ConfigHandler.getProperty("startX"));
		startY = Double.parseDouble(ConfigHandler.getProperty("startY"));
		startZ = Double.parseDouble(ConfigHandler.getProperty("startZ"));
		currentPosition = new Point3D(startX, startY, startZ);
		
		transmissionTheta1 = Double.parseDouble(ConfigHandler.getProperty("transmissionTheta1"));
		transmissionTheta2 = Double.parseDouble(ConfigHandler.getProperty("transmissionTheta2"));
		transmissionTheta3 = Double.parseDouble(ConfigHandler.getProperty("transmissionTheta3"));
		transmissionTheta4 = Double.parseDouble(ConfigHandler.getProperty("transmissionTheta4"));
		
		interval = Double.parseDouble(ConfigHandler.getProperty("interval"));
		
		maxSpeedOnCoordinateSystem = Double.parseDouble(ConfigHandler.getProperty("maxSpeedOnCoordinateSystem"));
		maxSpeedEffector = Double.parseDouble(ConfigHandler.getProperty("maxSpeedEffector"));
		
		dualshockStopRange = Double.parseDouble(ConfigHandler.getProperty("dualshockStopRange"));
		
		theta4Automatic = Boolean.parseBoolean(ConfigHandler.getProperty("theta4Automatic"));
		
		// Pro Sekunde
		maxSpeedMotor = Double.parseDouble(ConfigHandler.getProperty("maxSpeedMotor"));
		
		mode = Integer.parseInt(ConfigHandler.getProperty("mode"));
	}
	public static double getA1() {
		return a1;
	}
	
	public static void setA1(double a1) {
		Statics.a1 = a1;
	}
	
	public static double getA2() {
		return a2;
	}
	
	public static void setA2(double a2) {
		Statics.a2 = a2;
	}
	
	public static double getA5() {
		return a5;
	}
	
	public static void setA5(double a5) {
		Statics.a5 = a5;
	}
	public static double getA6() {
		return a6;
	}
	
	public static void setA6(double a6) {
		Statics.a6 = a6;
	}

	public static double getTransmissionTheta1() {
		return transmissionTheta1;
	}

	public static void setTransmissionTheta1(double transmissionTheta1) {
		Statics.transmissionTheta1 = transmissionTheta1;
	}

	public static double getTransmissionTheta2() {
		return transmissionTheta2;
	}

	public static void setTransmissionTheta2(double transmissionTheta2) {
		Statics.transmissionTheta2 = transmissionTheta2;
	}

	public static double getTransmissionTheta3() {
		return transmissionTheta3;
	}

	public static void setTransmissionTheta3(double transmissionTheta3) {
		Statics.transmissionTheta3 = transmissionTheta3;
	}

	public static double getTransmissionTheta4() {
		return transmissionTheta4;
	}

	public static void setTransmissionTheta4(double transmissionTheta4) {
		Statics.transmissionTheta4 = transmissionTheta4;
	}

	public static double getInterval() {
		return interval;
	}

	public static void setInterval(double interval) {
		Statics.interval = interval;
	}

	public static double getMaxSpeedOnCoordinateSystem() {
		return maxSpeedOnCoordinateSystem;
	}

	public static void setMaxSpeedOnCoordinateSystem(double maxSpeedOnCoordinateSystem) {
		Statics.maxSpeedOnCoordinateSystem = maxSpeedOnCoordinateSystem;
	}

	public static double getDualshockStopRange() {
		return dualshockStopRange;
	}

	public static void setDualshockStopRange(double dualshockStopRange) {
		Statics.dualshockStopRange = dualshockStopRange;
	}

	public static int getMaxAcceleration() {
		return maxAcceleration;
	}

	public static void setMaxAcceleration(int maxAcceleration) {
		Statics.maxAcceleration = maxAcceleration;
	}

	public static String getiPBrickLeft() {
		return iPBrickLeft;
	}

	public static void setiPBrickLeft(String iPBrickLeft) {
		Statics.iPBrickLeft = iPBrickLeft;
	}

	public static String getiPBrickRight() {
		return iPBrickRight;
	}

	public static void setiPBrickRight(String iPBrickRight) {
		Statics.iPBrickRight = iPBrickRight;
	}

	public static double getStartX() {
		return startX;
	}

	public static void setStartX(double startX) {
		Statics.startX = startX;
	}

	public static double getStartY() {
		return startY;
	}

	public static void setStartY(double startY) {
		Statics.startY = startY;
	}

	public static double getStartZ() {
		return startZ;
	}

	public static void setStartZ(double startZ) {
		Statics.startZ = startZ;
	}

	public static double getCurrentX() {
		return currentX;
	}

	public static void setCurrentX(double currentX) {
		Statics.currentX = currentX;
	}

	public static double getCurrentY() {
		return currentY;
	}

	public static void setCurrentY(double currentY) {
		Statics.currentY = currentY;
	}

	public static double getCurrentZ() {
		return currentZ;
	}

	public static void setCurrentZ(double currentZ) {
		Statics.currentZ = currentZ;
	}

	public static Point3D getCurrentPosition() {
		return currentPosition;
	}
	public static void setCurrentPosition(Point3D currentPosition) {
		Statics.currentPosition = currentPosition;
	}
	public static boolean isTheta4Automatic() {
		return theta4Automatic;
	}

	public static void setTheta4Automatic(boolean theta4Automatic) {
		Statics.theta4Automatic = theta4Automatic;
	}

	public static double getMaxSpeedMotor() {
		return maxSpeedMotor;
	}

	public static void setMaxSpeedMotor(double maxSpeedMotor) {
		Statics.maxSpeedMotor = maxSpeedMotor;
	}

	public static int getMode() {
		return mode;
	}

	public static void setMode(int mode) {
		Statics.mode = mode;
	}

	public static double getMaxSpeedEffector() {
		return maxSpeedEffector;
	}

	public static void setMaxSpeedEffector(double maxSpeedEffector) {
		Statics.maxSpeedEffector = maxSpeedEffector;
	}

	public static String getPropertyFile() {
		return propertyFile;
	}

	public static void setPropertyFile(String propertyFile) {
		Statics.propertyFile = propertyFile;
	}
}
