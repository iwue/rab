	package local.rab;

import java.util.LinkedList;
import javafx.geometry.Point3D;
import local.rab.config.Statics;

public class MoveTest {
	private LinkedList<Point3D> point3ds;

	public MoveTest() {
		point3ds = new LinkedList<Point3D>();
	}

	public void generalTestList() {
		 // Rotation Links
		point3ds.add(new Point3D(0, 304, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		 
		// Rotation Rechts
		point3ds.add(new Point3D(0, -304, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		  		  
		  // Gerade Links
		point3ds.add(new Point3D(304, 250, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
		  // Gerade Rechts 
		point3ds.add(new Point3D(304, -250, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
		  // Runter 
		point3ds.add(new Point3D(304, 0, 250));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
		  // Oben 
		point3ds.add(new Point3D(304, 0, 500));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
		  // Vorne 
		point3ds.add(new Point3D(400, 0, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
		  // Hinten 
		point3ds.add(new Point3D(200, 0, 428));
		point3ds.add(new Point3D(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ()));  
		
	}
	
	public LinkedList<Point3D> getPoint3ds() {
		return point3ds;
	}
}
