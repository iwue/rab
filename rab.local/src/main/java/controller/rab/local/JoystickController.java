package controller.rab.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcabi.aspects.Timeable;

import exceptions.rab.local.MultipleObjects;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class JoystickController {
	
	private Controller connectectedController;
	private int position = 0;
	private static Logger logger = LogManager.getLogger(JoystickController.class);

    /**
     * Search (and save) for controllers of type Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL and Controller.Type.FINGERSTICK.
     */
	private List<Controller> searchJoysticks() {
		List<Controller> foundControllers = new ArrayList<>();
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
        for(int i = 0; i < controllers.length; i++){
            Controller controller = controllers[i];

            if (
                    controller.getType() == Controller.Type.STICK || 
                    controller.getType() == Controller.Type.GAMEPAD || 
                    controller.getType() == Controller.Type.WHEEL ||
                    controller.getType() == Controller.Type.FINGERSTICK
               )
            {
            		foundControllers.add(controller);
                               
//                foundControllers.forEach(
//                		(con) -> Arrays.asList(con.getComponents()).forEach(
//                				(component) -> logger.info(con.getName()
//                						+"\n\tComponent:\t"+component.getName()
//                						+"\n\tID:\t\t"+component.getIdentifier()
//                						+"\n\tDeadzone:\t"+component.getDeadZone()
//                						+"\n\tPolldata:\t"+component.getPollData())));
            }
        }
        
        // Ausgabe alles Controller in Log
        foundControllers.forEach(
        		controller -> logger.info("Matching Joytick: " + controller.getName()));
        
        return foundControllers;
    }
	
	

	/**
	 * Wartet bis sich ein Controller vom Typ Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL oder Controller.Type.FINGERSTICK
     * vebunden hat.
     * 
     * Das Objekt {@value currentController} wird danach gesetzt.
	 * @throws MultipleObjects Es wurden mehere Controller gefunden.
	 * @throws Exception Restlichen Fehler
	 */
	public void connect(int positon) throws MultipleObjects, TimeoutException, InterruptedException{
		boolean isConnected = false;

		System.out.print("Searching for Controller.");
		for(int i = 0; i <= 10; i++) {
			List<Controller> controllers = this.searchJoysticks();
			
			if (!controllers.isEmpty()) {
				// Solange nur ein Controller verbunden ist, wird dieser genutzt
				if(controllers.size() == 1) {
					connectectedController = controllers.get(positon);
					return;
				} else {
					throw new MultipleObjects("Es wurden mehrere Controller gefunden");
				}
			}
			Thread.sleep(500);
			System.out.print(".");
		}
		throw new TimeoutException();
	}

	public Controller getConnectectedController() {
		return connectectedController;
	}

	public void setConnectectedController(Controller connectectedController) {
		this.connectectedController = connectectedController;
	}
}
