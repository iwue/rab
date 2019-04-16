package local.rab.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import local.rab.RandomGenerator;
import local.rab.windows.panes.motors.speed.DefaultMotordataModel;
import local.rab.windows.panes.motors.speed.MotordataModel;
import local.rab.windows.panes.motors.speed.SeriesChartPane;

import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.Random;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class WindowsMonitor extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowsMonitor frame = new WindowsMonitor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public WindowsMonitor() {
		RandomGenerator randomGenerator = new RandomGenerator();
		
		MotordataModel motordataModel_Theta1 = new DefaultMotordataModel(randomGenerator);
		MotordataModel motordataModel_Theta2 = new DefaultMotordataModel(randomGenerator);
		MotordataModel motordataModel_Theta3 = new DefaultMotordataModel(randomGenerator);
		MotordataModel motordataModel_Theta4 = new DefaultMotordataModel(randomGenerator);
		
		setResizable(true);
		setTitle("Real Time Monitor - RAB Roboterarm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 2000, 1000);
		
		JTabbedPane mainTab = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(mainTab, BorderLayout.CENTER);
		
		JPanel monitorTab = new JPanel();
		mainTab.addTab("Realtime Monitoring", null, monitorTab, null);
		monitorTab.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblSpeedGraphs = new JLabel("Speed-Graphs");
		monitorTab.add(lblSpeedGraphs);
		
		JLabel lblRotationGraphs = new JLabel("Rotation-Graphs");
		monitorTab.add(lblRotationGraphs);
		
		SeriesChartPane seriesChartPane_Theta1 = new SeriesChartPane(motordataModel_Theta1, "Speed Theta 1");
		FlowLayout flowLayout_theta1 = (FlowLayout) seriesChartPane_Theta1.getLayout();
		flowLayout_theta1.setAlignment(FlowLayout.LEADING);
		flowLayout_theta1.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta1);
		
		monitorTab.add(new JLabel("Platzhalter"));
		
		SeriesChartPane seriesChartPane_Theta2 = new SeriesChartPane(motordataModel_Theta2, "Speed Theta 2");
		FlowLayout flowLayout_theta2 = (FlowLayout) seriesChartPane_Theta2.getLayout();
		flowLayout_theta2.setAlignment(FlowLayout.LEADING);
		flowLayout_theta2.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta2);
		
		
		monitorTab.add(new JLabel("Platzhalter"));
		
		SeriesChartPane seriesChartPane_Theta3 = new SeriesChartPane(motordataModel_Theta3, "Speed Theta 3");
		FlowLayout flowLayout_theta3 = (FlowLayout) seriesChartPane_Theta3.getLayout();
		flowLayout_theta3.setAlignment(FlowLayout.LEADING);
		flowLayout_theta3.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta3);
		
		monitorTab.add(new JLabel("Platzhalter"));
		
		SeriesChartPane seriesChartPane_Theta4 = new SeriesChartPane(motordataModel_Theta4, "Speed Theta 4");
		FlowLayout flowLayout_theta4 = (FlowLayout) seriesChartPane_Theta4.getLayout();
		flowLayout_theta4.setAlignment(FlowLayout.LEADING);
		flowLayout_theta4.setAlignOnBaseline(true);
		monitorTab.add(seriesChartPane_Theta4);
		
		
		JPanel settingsTab = new JPanel();
		mainTab.addTab("Settings", null, settingsTab, null);
	}
}
