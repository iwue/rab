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

import controller.rab.local.EvController;
import controller.rab.local.JoystickController;
import exceptions.rab.local.MultipleObjects;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class main {

	protected Shell shell;
	private static Logger logger = LogManager.getLogger(main.class);
	private JoystickController ps4c = new JoystickController(); 
	private EvController evC = new EvController();
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {			
			
			main window = new main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Open the window.
	 */
	public void open() {
		// Verbinden mit dem Controller und EV3
		try {
			ps4c.connectToHardware();
			evC.connectToHardware();
			
			evC.getEv3().getTextLCD().drawString("Projekt RAB\nJuri Stadler\nIsaac Würth\nPascalHelfenberger", 5, 5);
		} catch (MultipleObjects e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
}
