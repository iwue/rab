package controller.rab.local;

import java.security.InvalidParameterException;
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

	private Controller controller;
	private static Logger logger = LogManager.getLogger(JoystickController.class);

	/**
	 * Wartet bis sich ein Controller vom Typ Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL oder Controller.Type.FINGERSTICK
     * vebunden hat.
     * 
     * Das Objekt {@value currentController} wird danach gesetzt.
	 * @throws MultipleObjects Es wurden mehere Controller gefunden.
	 * @throws Exception Restlichen Fehler
	 */
	public JoystickController(int position) throws MultipleObjects, TimeoutException, InterruptedException{

		System.out.print("Searching for Controller.");
		for(int i = 0; i <= 10; i++) {
			List<Controller> controllers = this.searchJoysticks();
			
			if (!controllers.isEmpty()) {
				// Solange nur ein Controller verbunden ist, wird dieser genutzt
				if(controllers.size() <= (position + 1)) {
					controller = controllers.get(position);
					return;
				} else {
					throw new InvalidParameterException("Parameter Position ist ungültig. Anzahl Joysticks: " + controllers.size());
				}
			}
			Thread.sleep(500);
			System.out.print(".");
		}
		throw new TimeoutException("Es wurden keine Joysticks gefunden.");
	}
	
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

	public Controller getController() {
		return controller;
	}
}
