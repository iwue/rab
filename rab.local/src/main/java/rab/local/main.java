package rab.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import controller.rab.local.BrickController;
import controller.rab.local.JoystickController;
import exceptions.rab.local.MultipleObjects;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class main {

	protected Shell shell;
	private static Logger logger = LogManager.getLogger(main.class);
	private JoystickController joystick;
	private BrickController brick;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {			
			
			main window = new main();
			window.open();
		} catch (Exception e) {
			logger.error(e);
		} 
	}

	/**
	 * Open the window.
	 */
	
	public void open() {
		// Verbinden mit dem Controller und EV3
		try {
			joystick = new JoystickController(0);
			brick = new BrickController("10.0.10.0");
			
			joystick.connect(0);
			brick.connect();
			brick.getBrick().getLED().setPattern(1);
			RMIRegulatedMotor motor = brick.getBrick().createRegulatedMotor("C", 'L');
			RMISampleProvider gyrosSample = brick.getBrick().createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor","Touch");
			
			brick.getBrick().getGraphicsLCD().clear();
			brick.getBrick().getGraphicsLCD().drawString("Projekt RAB", 2, 20, 5);
			brick.getBrick().getGraphicsLCD().drawString("Juri,Isaac,Pascal", 2, 35, 5);
			brick.getBrick().getGraphicsLCD().drawString("Ready...", 2, 50, 5);
			brick.getBrick().getLED().setPattern(4);
			
			motor.setAcceleration(200);
			float conCur = 0;
			int speed = 0;
			int maxSpeed = 300;
			
			while(true) {
				joystick.getController().poll();
				conCur = joystick.getController().getComponents()[2].getPollData();
				speed = (int) (maxSpeed * conCur);
				
				
				if (conCur > -0.08 && conCur < 0.08) {
					System.out.println("stop: " + speed);
					motor.stop(true);
				} else  if(conCur >= -1 && conCur <= 1){
					if(speed > 0) {
						System.out.println("forward: " + speed);
						motor.forward();
						motor.setSpeed(speed);
					} else {
						System.out.println("backward: " + speed);
						motor.backward();
						motor.setSpeed(speed);
					}
				}
				
				if(joystick.getController().getComponents()[6].getPollData() == 1.0f) {
					break;
				}
				
				System.out.println("Tacho:\t"+motor.getTachoCount());
				
				
				float[] sample = gyrosSample.fetchSample();
				
				System.out.println("GyrosSensor:"+sample[0]);
				
				if(sample[0] == 1.0) {
					motor.setAcceleration(2000);
					motor.stop(true);
					break;
				}
			}
			
			motor.close();
			gyrosSample.close();
			
			brick.getBrick().getLED().setPattern(1);
			System.exit(0);;

		} catch (MultipleObjects e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(635, 450);
		shell.setText("SWT Application");
	}
	
	public static int getAxisValueInPercentage(float axisValue)
    {
        return (int)(((2 - (1 - axisValue)) * 100) / 2);
    }
}
