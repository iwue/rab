	package local.rab;

import java.util.LinkedList;

import org.j3d.maths.vector.Point3d;

import local.rab.config.Statics;

public class MoveTest {
	private LinkedList<Point3d> point3ds;

	public MoveTest() {
		point3ds = new LinkedList<Point3d>();
	}

	public void generalTestList() {
		Point3d point3d = new Point3d();
		Point3d startPositon = new Point3d();
		startPositon.set(Statics.getStartX(), Statics.getStartY(), Statics.getCurrentZ());
		// Rotation Links
		
		point3d = new Point3d();
		point3d.set(0, 304, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);  
		 
		// Rotation Rechts
		point3d = new Point3d();
		point3d.set(0, -304, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);  
		  		  
		  // Gerade Links
		point3d = new Point3d();
		point3d.set(304, 250, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);  
		
		  // Gerade Rechts 
		point3d = new Point3d();
		point3d.set(304, -250, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);
		
		  // Runter 
		point3d = new Point3d();
		point3d.set(304, 0, 250);
		point3ds.add(point3d);
		point3ds.add(startPositon);
		
		  // Oben 
		point3d = new Point3d();
		point3d.set(304, 0, 500);
		point3ds.add(point3d);
		point3ds.add(startPositon);
		
		  // Vorne 
		point3d = new Point3d();
		point3d.set(400, 0, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);
		
		  // Hinten 
		point3d = new Point3d();
		point3d.set(200, 0, 428);
		point3ds.add(point3d);
		point3ds.add(startPositon);
	}
	
	public LinkedList<Point3d> getPoint3ds() {
		return point3ds;
	}
}
