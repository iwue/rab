package local.rab.devices.dualshock;

import net.java.games.input.Controller;



/**
 * @author IsaacWürth
 *
 * TODO Zuweisen der Array ID in Component
 */
public class DualshockSimple {
	private Controller controller;
	private float isPressed = 1f;
	
	public DualshockSimple(Controller joystick) {
		super();
		this.controller = joystick;
	}
	
	private void poll() {
		controller.poll();
	}

	public float getLeftStickX() {
		poll();
		float data = controller.getComponents()[3].getPollData();
		return data;
	}

	public float getLeftStickY() {
		poll();
		float data = controller.getComponents()[2].getPollData();
		return data;
	}

	public float getRightStickX() {
		poll();
		float data = controller.getComponents()[1].getPollData();
		return data;
	}

	public float getRightStickY() {
		poll();
		float data = controller.getComponents()[0].getPollData();
		return data;
	}

	public float getR1() {
		poll();
		float data = controller.getComponents()[10].getPollData();
		return data;
	}

	public float getR2() {
		poll();
		float data = controller.getComponents()[19].getPollData();
		return data;
	}

	public float getR3() {
		poll();
		float data = controller.getComponents()[16].getPollData();
		return data;
	}

	public float getL1() {
		poll();
		float data = controller.getComponents()[9].getPollData();
		return data;
	}

	public float getL2() {
		poll();
		float data = controller.getComponents()[20].getPollData();
		return data;
	}

	public float getL3() {
		poll();
		float data = controller.getComponents()[15].getPollData();
		return data;
	}

	public boolean isPressedActionCross() {
		poll();
		boolean status = controller.getComponents()[6].getPollData() == isPressed;
		return status;
	}

	public boolean isPressedActionSquare() {
		poll();
		boolean status = controller.getComponents()[5].getPollData() == isPressed;
		return status;
	}

	public boolean isPressedActionCirle() {
		poll();
		boolean status = controller.getComponents()[7].getPollData() == isPressed;
		return status;	}

	public boolean isPressedActionTriangle() {
		poll();
		boolean status = controller.getComponents()[8].getPollData() == isPressed;
		return status;
		}

	public boolean isPressedDPadLeft() {
		poll();
		
		boolean status = false;
		double data = controller.getComponents()[4].getPollData();
		if (data == 1.0 
				|| data == 0.875
				|| data == 0.125) {
			status = true;
		}
		return status;	
	}

	public boolean isPressedDPardUp() {
		poll();
		
		boolean status = false;
		double data = controller.getComponents()[4].getPollData();
		if (data == 0.25 
				|| data == 0.125
				|| data == 0.375) {
			status = true;
		}
		
		return status;	
	}

	public boolean isPressedDPadRight() {
		poll();
		
		boolean status = false;
		double data = controller.getComponents()[4].getPollData();
		if (data == 0.5 
				|| data == 0.375
				|| data == 0.625) {
			status = true;
		}
		
		return status;
	}

	public boolean isPressedDPadDown() {
		poll();
		boolean status = false;
		double data = controller.getComponents()[4].getPollData();
		if (data == 0.75 
				|| data == 0.875
				|| data == 0.625) {
			status = true;
		}
		return status;	
	}

	public boolean isPressedButtonShare() {
		poll();
		boolean status = controller.getComponents()[13].getPollData() == isPressed;
		return status;	
	}

	public boolean isPressedButtonPS() {
		poll();
		boolean status = controller.getComponents()[12].getPollData() == isPressed;
		return status;	
	}

	public boolean isPressedButtonOption() {
		poll();
		boolean status = controller.getComponents()[14].getPollData() == isPressed;
		return status;	
	}
}