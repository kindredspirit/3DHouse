package house.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import house.view.MrCamera;

/**
 * Class KeyEvents - describes what to do with the camera upon changes is key states
 * @extends KeyAdapter - the thing that apparently adapts to your keys
 * @author Moebot (Brittany Alkire)
 * Created: 13 Sept 2011
 * Edited: 21 Oct 2011 - STRAFING NOW WORKS! (or perhaps more accurately, i quit being stupid)
 */
public class KeyEvents extends KeyAdapter {
	double phi, theta; // view direction angles
	float distance;
	final double ANGLE_INCR = Math.PI / 18;
	float[] eye; // position of viewer's eye
	float[] lookv; // direction to look in
	float[] look; // point looked at
	float[] up; // which way is up?
	
	private KeyBindings bindings = new KeyBindings();

	/*****************************************************************************************************************
	 * Constructor KeyEvents() - sets initial angle and vector values to correspond with those from the camera
	 * @param camera - calculating viewing angles with respect to the direction in which the camera is looking.
	 *****************************************************************************************************************/
	public KeyEvents(MrCamera camera) {
		phi = camera.phi;
		theta = camera.theta;
		distance = camera.distance;
		eye = camera.eye;
		lookv = camera.lookv;
		look = camera.look;
		up = camera.up;
	}

	/*********************************************************************************
	 * keyPressed()
	 * @param KeyEvent event - the event
	 * Describes each key state action and for which key to bind them.
	 ********************************************************************************/
	public void keyPressed(KeyEvent event) {
		//Integer that corresponds with the hash map keycode
		Integer bindingID = bindings.getBindings().get(
				KeyEvent.getKeyText(event.getKeyCode()));
		//System.out.println(event);
		if (bindingID == null) {
			return;
		}

		//RESET CAMERA
		if(bindingID == 10)
		{
			MrCamera.eye[0] = 2.0f;
			MrCamera.eye[1]= 3.0f;
			MrCamera.eye[2] = 20.0f;
		}
		
		// LOOK_UP (Pitch)
		if (bindingID == 4) {
			// decremenet angle
			this.phi -= ANGLE_INCR;
			if (phi <= 0) {
				this.phi += 2 * Math.PI;
			}

			this.lookv[1] = (float) Math.cos(this.phi);
			this.lookv[0] = (float) (Math.sin(this.phi) * Math.sin(this.theta));
			this.lookv[2] = (float) (Math.sin(this.phi) * Math.cos(this.theta));

			// compute up vector
			this.up[0] = (float) (-Math.sin(this.theta) * Math.cos(this.phi));
			this.up[1] = (float) Math.sin(this.phi);
			this.up[2] = (float) (-Math.cos(this.theta) * Math.cos(this.phi));

			// compute point looked at
			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[1] = this.eye[1] + this.lookv[1];
			this.look[2] = this.eye[2] + this.lookv[2];

		}
		
		// LOOK_DOWN (Pitch)
		else if (bindingID == 5) {
			// increment angle
			this.phi += ANGLE_INCR;
			if (this.phi >= 2 * Math.PI) {
				this.phi = 2 * Math.PI - this.phi; // wrap angle back
			}
			this.lookv[1] = (float) Math.cos(this.phi);
			this.lookv[0] = (float) (Math.sin(this.phi) * Math.sin(this.theta));
			this.lookv[2] = (float) (Math.sin(this.phi) * Math.cos(this.theta));

			// compute up vector
			this.up[0] = (float) (-Math.sin(this.theta) * Math.cos(this.phi));
			this.up[1] = (float) Math.sin(this.phi);
			this.up[2] = (float) (-Math.cos(this.theta) * Math.cos(this.phi));

			// compute point looked at
			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[1] = this.eye[1] + this.lookv[1];
			this.look[2] = this.eye[2] + this.lookv[2];
		}
		
		// LOOK_LEFT (Yaw)
		else if (bindingID == 6) {
			// increment angle
			this.theta += ANGLE_INCR;

			// compute view direction vector
			this.lookv[0] = (float) (Math.sin(this.phi) * Math.sin(this.theta));
			this.lookv[2] = (float) (Math.sin(this.phi) * Math.cos(this.theta));

			// compute up vector
			this.up[0] = (float) (-Math.sin(this.theta) * Math.cos(this.phi));
			this.up[2] = (float) (-Math.cos(this.theta) * Math.cos(this.phi));

			// compute point looked at
			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[2] = this.eye[2] + this.lookv[2];
		}
		
		// LOOK_RIGHT (Yaw)
		else if (bindingID == 7) {
			// decrement angle by 10 degree
			this.theta -= ANGLE_INCR;

			// compute view direction vector
			this.lookv[0] = (float) (Math.sin(this.phi) * Math.sin(this.theta));
			this.lookv[2] = (float) (Math.sin(this.phi) * Math.cos(this.theta));

			// compute up vector
			this.up[0] = (float) (-Math.sin(this.theta) * Math.cos(this.phi));
			this.up[2] = (float) (-Math.cos(this.theta) * Math.cos(this.phi));

			// compute point looked at
			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[2] = this.eye[2] + this.lookv[2];

		}
		
		//STRAFE_LEFT - kinda works
		else if(bindingID == 2)
		{
			// compute position
	        this.eye[1]=this.eye[1]+(this.distance*this.lookv[1]);

	        	this.eye[2]=this.eye[2]+(-1*this.distance*this.lookv[0]);
	        	this.eye[0]=this.eye[0]+(this.distance*this.lookv[2]);
	        
	        // compute point looked at
	        this.look[0]=this.eye[0]+this.lookv[0];
	        this.look[1]=this.eye[1]+this.lookv[1];
	        this.look[2]=this.eye[2]+this.lookv[2];
		}
		
		//STRAFE_RIGHT - kinda works
		else if(bindingID == 3)
		{
			// compute position
	        this.eye[1]=this.eye[1]+(this.distance*this.lookv[1]);
        	this.eye[2]=this.eye[2]+(this.distance*this.lookv[0]);
        	this.eye[0]=this.eye[0]+(-1*this.distance*this.lookv[2]);
        	// compute point looked at
	        this.look[0]=this.eye[0]+this.lookv[0];
	        this.look[1]=this.eye[1]+this.lookv[1];
	        this.look[2]=this.eye[2]+this.lookv[2];
		}
		
		// MOVE_FORWARD
		else if (bindingID == 0) {
			this.eye[0] = this.eye[0] + this.distance * this.lookv[0];
			this.eye[1] = this.eye[1] + this.distance * this.lookv[1];
			this.eye[2] = this.eye[2] + this.distance * this.lookv[2];

			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[1] = this.eye[1] + this.lookv[1];
			this.look[2] = this.eye[2] + this.lookv[2];
		}

		// MOVE_BACKWARD
		else if (bindingID == 1) {
			this.eye[0] = this.eye[0] - this.distance * this.lookv[0];
			this.eye[1] = this.eye[1] - this.distance * this.lookv[1];
			this.eye[2] = this.eye[2] - this.distance * this.lookv[2];

			this.look[0] = this.eye[0] + this.lookv[0];
			this.look[1] = this.eye[1] + this.lookv[1];
			this.look[2] = this.eye[2] + this.lookv[2];
		}

		// move up
		else if (bindingID == 8) {
			this.eye[1] = this.eye[1] + this.distance;
			this.look[1] = this.eye[1] + this.lookv[1];
		}
		// move down
		else if (bindingID == 9) {
			this.eye[1] = this.eye[1] - this.distance;
			this.look[1] = this.eye[1] + this.lookv[1];
		}
	}
	
	//unimplemented method
	public void keyTyped(KeyEvent event) {
	}
	//unimplemented method
	public void keyReleased(KeyEvent event) {

	}

}
