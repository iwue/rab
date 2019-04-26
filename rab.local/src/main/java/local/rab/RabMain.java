package local.rab;

import java.awt.Frame;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import org.j3d.maths.vector.Point3d;

import local.rab.config.Statics;
import local.rab.controller.CheckCoordinatesHandler;
import local.rab.controller.MoveSimpleController;
import local.rab.controller.RabMainController;
import local.rab.controller.calculation.CalculateAngelsToCoordinate;
import local.rab.controller.threads.ThreadEffector;
import local.rab.controller.threads.ThreadTheta1;
import local.rab.controller.threads.ThreadTheta2;
import local.rab.controller.threads.ThreadTheta3;
import local.rab.controller.threads.ThreadTheta4;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.dualshock.DualshockController;
import local.rab.devices.dualshock.DualshockSimple;
import local.rab.windows.WindowsMonitor;
import local.rab.windows.InfoFront;


public class RabMain {
	private RabMainController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickComponentHandler brickController;
	private MoveSimpleController moveSimple;

	private Thread tTheta1;
	private Thread tTheta2;
	private Thread tTheta3;
	private Thread tTheta4;
	private Thread tThetaEffector;
	private Thread tInformation;
	private JFrame jMainFrame;
	private InfoFront jInfo;
	private CalculateAngelsToCoordinate calculateAngelsToCoordinate;
	private CheckCoordinatesHandler checkCoordinatesHandler;

	private MoveTest moveTest;

	public static void main(String[] args) {
		RabMain main = new RabMain();
		main.rab();
	}

	public void rab() {
		try {
			jInfo = new InfoFront();
			jInfo.getInfoBox().setText("System starting...");
			jInfo.setAlwaysOnTop(true);
			jInfo.repaint();
			jInfo.setLocationRelativeTo(null);
			jInfo.setResizable(false);
			jInfo.setVisible(true);
			jInfo.toFront();
			
			Statics.initProperties();
			dualshockController = new DualshockController(0);
			dualshockSimple = new DualshockSimple(dualshockController.getController());
			brickController = new BrickComponentHandler();
			rabController = new RabMainController(dualshockSimple, brickController);
			moveSimple = new MoveSimpleController(dualshockSimple, brickController);
			moveTest = new MoveTest();
			moveTest.generalTestList();
			calculateAngelsToCoordinate = new CalculateAngelsToCoordinate();
			checkCoordinatesHandler = new CheckCoordinatesHandler();

			if(Statics.getMode() == 1) {
			
				tTheta1 = new Thread(new ThreadTheta1(brickController));
				tTheta1.setName("Theta 1");
				tTheta1.start();
	
				tTheta2 = new Thread(new ThreadTheta2(brickController));
				tTheta2.setName("Theta 2");
				tTheta2.start();
	
				tTheta3 = new Thread(new ThreadTheta3(brickController));
				tTheta3.setName("Theta 3");
				tTheta3.start();
			}
			
			tTheta4 = new Thread(new ThreadTheta4(brickController));
			tTheta4.setName("Theta 4");
			tTheta4.start();
			
			tThetaEffector = new Thread(new ThreadEffector(dualshockSimple, brickController));
			tThetaEffector.setName("Effector");
			tThetaEffector.start();	
			
			jMainFrame = new WindowsMonitor(brickController);
			jMainFrame.setVisible(true);
			jMainFrame.setExtendedState(Frame.MAXIMIZED_BOTH); 
			
			jInfo.setVisible(false);
			
			jMainFrame.toFront();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (true) {
			if (Statics.getMode() == 1) {
				
				rabController.move();
				
				// Home Position
				if (dualshockSimple.isPressedActionSquare()) {
					
					Point3d currentPosition = new Point3d();
							currentPosition.set(Statics.getStartX(), 
															Statics.getStartY(),
															Statics.getStartZ());
					Statics.setCurrentPosition(currentPosition);
				}
				
				if(dualshockSimple.isPressedActionCirle()) {
					Statics.setMode(2);
					tTheta1.interrupt();
					tTheta2.interrupt();
					tTheta3.interrupt();
					Statics.setTheta4Automatic(true);
					
					jInfo.getInfoBox().setText("Switch to Mode 2");
					jInfo.repaint();
					jInfo.setLocationRelativeTo(null);
					jInfo.setResizable(false);
					jInfo.setVisible(true);
					jInfo.toFront();
					
					try {
						Thread.sleep(1000);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					jInfo.setVisible(false);
				}
			} else if (Statics.getMode() == 2) {
				Point3d currentPosition = calculateAngelsToCoordinate.calc(brickController);
				moveSimple.move();

				if (!checkCoordinatesHandler.isCoordinateValid(currentPosition)) {
					brickController.getBrickLeft().getBrick().getLED().setPattern(5);
					brickController.getBrickRight().getBrick().getLED().setPattern(5);
					
					jInfo.getInfoBox().setText("Out of Range.");
					jInfo.repaint();
					jInfo.setLocationRelativeTo(null);
					jInfo.setResizable(false);
					jInfo.setVisible(true);
					jInfo.toFront();
				} else {
					brickController.getBrickLeft().getBrick().getLED().setPattern(2);
					brickController.getBrickRight().getBrick().getLED().setPattern(2);
					jInfo.setVisible(false);
				}
				
				if(dualshockSimple.isPressedActionCirle()) {
					if (checkCoordinatesHandler.isCoordinateValid(currentPosition)) {
						Statics.setMode(1);
						Statics.setCurrentPosition(currentPosition);	
						Statics.setTheta4Automatic(false);
						
						tTheta1 = new Thread(new ThreadTheta1(brickController));
						tTheta1.setName("Theta 1");
						tTheta1.start();
			
						tTheta2 = new Thread(new ThreadTheta2(brickController));
						tTheta2.setName("Theta 2");
						tTheta2.start();	
			
						tTheta3 = new Thread(new ThreadTheta3(brickController));
						tTheta3.setName("Theta 3");
						tTheta3.start();
						
						jInfo.getInfoBox().setText("Switch to Mode 1"); 
						jInfo.repaint();
						jInfo.setLocationRelativeTo(null);
						jInfo.setResizable(false);
						jInfo.setVisible(true);
						jInfo.toFront();
						
						brickController.getBrickLeft().getBrick().getLED().setPattern(1);
						brickController.getBrickRight().getBrick().getLED().setPattern(1);
						
						try {
							Thread.sleep(1000);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
						jInfo.setVisible(false);
					} 
				}
			}
			
			// Shutdown
			if (dualshockSimple.isPressedActionCross()) {
				jInfo.getInfoBox().setText("System shutdown ...");
				jInfo.repaint();
				jInfo.setLocationRelativeTo(null);
				jInfo.setResizable(false);
				jInfo.setVisible(true);
				jInfo.toFront();
				
				jMainFrame.setVisible(false);
				
				if(Statics.getMode() == 2) {
					Point3d currentPosition = calculateAngelsToCoordinate.calc(brickController);
					Statics.setCurrentPosition(currentPosition);	
					Statics.setTheta4Automatic(false);
					
					tTheta1 = new Thread(new ThreadTheta1(brickController));
					tTheta1.setName("Theta 1");
					tTheta1.start();
		
					tTheta2 = new Thread(new ThreadTheta2(brickController));
					tTheta2.setName("Theta 2");
					tTheta2.start();
		
					tTheta3 = new Thread(new ThreadTheta3(brickController));
					tTheta3.setName("Theta 3");
					tTheta3.start();
				}
				
				Point3d currentPosition = new Point3d();
				currentPosition.set(Statics.getStartX(), 
						Statics.getStartY(),
						Statics.getStartZ());
				
				Statics.setCurrentPosition(currentPosition);
				
				try {
					Thread.sleep(1000);
					
					while(brickController.getHingTheta1().isMoving()
							|| brickController.getHingTheta20().isMoving()
							|| brickController.getHingTheta3().isMoving()
							|| brickController.getHingTheta4().isMoving()) {
					}
					
					tTheta1.interrupt();
					tTheta2.interrupt();
					tTheta3.interrupt();
					tTheta4.interrupt();
					brickController.closeHings();
					jInfo.setVisible(false);
					System.exit(0);
				} catch(InterruptedException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
