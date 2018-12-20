package controller.rab.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ps4controller {
	
	private List<Controller> foundControllers = new ArrayList<>();
	
	private static Logger logger = LogManager.getLogger(ps4controller.class);

    /**
     * Search (and save) for controllers of type Controller.Type.STICK,
     * Controller.Type.GAMEPAD, Controller.Type.WHEEL and Controller.Type.FINGERSTICK.
     */
	public Controller searchForControllers() {
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
                				(component) -> logger.info(con.getName()+" Component: "+component.getName())));
                // Add new controller to the list on the window.
                return foundControllers.get(0);
            }
        }
    }
	
	public void waitForController() {
		
	}
}
