package local.rab.windows;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import local.rab.config.Statics;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.windows.panes.coordinates.CoordinateModel;
import local.rab.windows.panes.coordinates.DefaultCoordinateModelXY;
import local.rab.windows.panes.coordinates.DefaultCoordinateModelZ;
import local.rab.windows.panes.coordinates.SeriesChartPaneXYZ;
import local.rab.windows.panes.motors.speed.DefaultMotordataModelAngle;
import local.rab.windows.panes.motors.speed.DefaultMotordataModelSpeed;
import local.rab.windows.panes.motors.speed.MotordataModel;
import local.rab.windows.panes.motors.speed.SeriesChartPane;

import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;

public class WindowsMonitor extends JFrame{
	private BrickComponentHandler  brickComponentHandler = null;
	
	/**
	 * Create the frame.
	 */
	public WindowsMonitor(BrickComponentHandler componentHandle) {
		brickComponentHandler = componentHandle;
		
		CoordinateModel coordinateModelXY = new DefaultCoordinateModelXY();
		CoordinateModel coordinateModelZ = new DefaultCoordinateModelZ();
		MotordataModel motordataModel_Theta1_Speed = new DefaultMotordataModelSpeed(brickComponentHandler.getHingTheta1());
		MotordataModel motordataModel_Theta2_Speed = new DefaultMotordataModelSpeed(brickComponentHandler.getHingTheta20());
		MotordataModel motordataModel_Theta3_Speed = new DefaultMotordataModelSpeed(brickComponentHandler.getHingTheta3());
		MotordataModel motordataModel_Theta4_Speed = new DefaultMotordataModelSpeed(brickComponentHandler.getHingTheta4());
		
		MotordataModel motordataModel_Theta1_Angle = new DefaultMotordataModelAngle(brickComponentHandler.getHingTheta1(), Statics.getTransmissionTheta1());
		MotordataModel motordataModel_Theta2_Angle = new DefaultMotordataModelAngle(brickComponentHandler.getHingTheta20(), Statics.getTransmissionTheta2());
		MotordataModel motordataModel_Theta3_Angle = new DefaultMotordataModelAngle(brickComponentHandler.getHingTheta3(), Statics.getTransmissionTheta3());
		MotordataModel motordataModel_Theta4_Angle = new DefaultMotordataModelAngle(brickComponentHandler.getHingTheta4(), Statics.getTransmissionTheta4() );
		
		
		setResizable(true);
		setTitle("Real Time Monitor - RAB Roboterarm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 2000, 1000);
		
		JTabbedPane mainTab = new JTabbedPane(SwingConstants.TOP);
		getContentPane().add(mainTab, BorderLayout.CENTER);
		
		JPanel monitorTab = new JPanel();
		mainTab.addTab("Realtime Monitoring", null, monitorTab, null);
		monitorTab.setLayout(new GridLayout(0, 2, 0, 0));
		
		SeriesChartPaneXYZ seriesChartPane_CoordinatesXY = new SeriesChartPaneXYZ(coordinateModelXY, "X and Y", -500, 500, -500, 500);
		FlowLayout flowLayout_XY = (FlowLayout) seriesChartPane_CoordinatesXY.getLayout();
		flowLayout_XY.setAlignment(FlowLayout.LEADING);
		flowLayout_XY.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_CoordinatesXY);
		
		SeriesChartPaneXYZ seriesChartPane_CoordinatesZ = new SeriesChartPaneXYZ(coordinateModelZ, "Z", -50, 50, 0, 600);
		FlowLayout flowLayout_Z = (FlowLayout) seriesChartPane_CoordinatesZ.getLayout();
		flowLayout_Z.setAlignment(FlowLayout.LEADING);
		flowLayout_Z.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_CoordinatesZ);
		
		SeriesChartPane seriesChartPane_Theta1_Speed = new SeriesChartPane(motordataModel_Theta1_Speed, "Speed Theta 1", 0.0, 200.0);
		FlowLayout flowLayout_theta1_Speed = (FlowLayout) seriesChartPane_Theta1_Speed.getLayout();
		flowLayout_theta1_Speed.setAlignment(FlowLayout.LEADING);
		flowLayout_theta1_Speed.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta1_Speed);
		
		SeriesChartPane seriesChartPane_Theta1_Angle = new SeriesChartPane(motordataModel_Theta1_Angle, "Angle Theta 1", -180, 180);
		FlowLayout flowLayout_theta1_Angle = (FlowLayout) seriesChartPane_Theta1_Angle.getLayout();
		flowLayout_theta1_Angle.setAlignment(FlowLayout.LEADING);
		flowLayout_theta1_Angle.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta1_Angle);
		
		SeriesChartPane seriesChartPane_Theta2 = new SeriesChartPane(motordataModel_Theta2_Speed, "Speed Theta 2", 0.0, 200.0);
		FlowLayout flowLayout_theta2 = (FlowLayout) seriesChartPane_Theta2.getLayout();
		flowLayout_theta2.setAlignment(FlowLayout.LEADING);
		flowLayout_theta2.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta2);
		
		
		SeriesChartPane seriesChartPane_Theta2_Angle = new SeriesChartPane(motordataModel_Theta2_Angle, "Angle Theta 2", -180, 180);
		FlowLayout flowLayout_theta2_Angle = (FlowLayout) seriesChartPane_Theta2_Angle.getLayout();
		flowLayout_theta2_Angle.setAlignment(FlowLayout.LEADING);
		flowLayout_theta2_Angle.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta2_Angle);
		
		SeriesChartPane seriesChartPane_Theta3 = new SeriesChartPane(motordataModel_Theta3_Speed, "Speed Theta 3", 0.0, 200.0);
		FlowLayout flowLayout_theta3 = (FlowLayout) seriesChartPane_Theta3.getLayout();
		flowLayout_theta3.setAlignment(FlowLayout.LEADING);
		flowLayout_theta3.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta3);
		
		SeriesChartPane seriesChartPane_Theta3_Angle = new SeriesChartPane(motordataModel_Theta3_Angle, "Angle Theta 3", -180, 180);
		FlowLayout flowLayout_theta3_Angle = (FlowLayout) seriesChartPane_Theta3_Angle.getLayout();
		flowLayout_theta3_Angle.setAlignment(FlowLayout.LEADING);
		flowLayout_theta3_Angle.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta3_Angle);
		
		SeriesChartPane seriesChartPane_Theta4 = new SeriesChartPane(motordataModel_Theta4_Speed, "Speed Theta 4", 0.0, 200.0);
		FlowLayout flowLayout_theta4 = (FlowLayout) seriesChartPane_Theta4.getLayout();
		flowLayout_theta4.setAlignment(FlowLayout.LEADING);
		flowLayout_theta4.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta4);
		
		SeriesChartPane seriesChartPane_Theta4_Angle = new SeriesChartPane(motordataModel_Theta4_Angle, "Angle Theta 4", -180, 180);
		FlowLayout flowLayout_theta4_Angle = (FlowLayout) seriesChartPane_Theta4_Angle.getLayout();
		flowLayout_theta4_Angle.setAlignment(FlowLayout.LEADING);
		flowLayout_theta4_Angle.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta4_Angle);
		
		JPanel settingsTab = new JPanel();
		mainTab.addTab("Settings", null, settingsTab, null);
	}
}
