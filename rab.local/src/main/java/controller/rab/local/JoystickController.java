package controller.rab.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcabi.aspects.Timeable;

import exceptions.rab.local.MultipleObjects;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class JoystickController {
	
	private List<Controller> foundControllers = new ArrayList<>();
	private Controller connectectedController;
	private static Logger logger = LogManager.getLogger(JoystickController.class);

    /**
     * Search (and save) for controllers of type Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL and Controller.Type.FINGERSTICK.
     */
	public void searchForControllers() {
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
                // Add new controller to the list of all controllers.
                foundControllers.add(controller);
            
                foundControllers.forEach(
                		(con) -> Arrays.asList(con.getComponents()).forEach(
                				(component) -> logger.info(con.getName()
                						+"\n\tComponent:\t"+component.getName()
                						+"\n\tID:\t\t"+component.getIdentifier()
                						+"\n\tDeadzone:\t"+component.getDeadZone()
                						+"\n\tPolldata:\t"+component.getPollData())));
            }
        }
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
	@Timeable(limit = 2, unit = TimeUnit.SECONDS)
	public void connectToHardware() throws MultipleObjects, Exception{
		boolean isConnected = false;

		System.out.print("Searching for Controller.");
		while(!isConnected) {
			searchForControllers();
			if (!foundControllers.isEmpty()) {
				// Solange nur ein Controller verbunden ist, wird dieser genutzt
				if(foundControllers.size() == 1) {
					connectectedController = foundControllers.get(0);
					return;
				} else {
					throw new MultipleObjects("Es wurden mehrere Controller gefunden");
				}
			}
			Thread.sleep(500);
			System.out.print(".");
		}
	}

	public List<Controller> getFoundControllers() {
		return foundControllers;
	}

	public void setFoundControllers(List<Controller> foundControllers) {
		this.foundControllers = foundControllers;
	}

	public Controller getConnectectedController() {
		return connectectedController;
	}

	public void setConnectectedController(Controller connectectedController) {
		this.connectectedController = connectectedController;
	}
}
