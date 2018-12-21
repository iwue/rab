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

import controller.rab.local.ControllerOp;
import exceptions.rab.local.MultipleObjects;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class main {

	protected Shell shell;
	private List<Controller> foundControllers;
	private static Logger logger = LogManager.getLogger(main.class);
	private ControllerOp ps4c = new ControllerOp(); 
	
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
		// Verb
		try {
			ps4c.waitForController();
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
