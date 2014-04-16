package house.listeners;
import java.util.HashMap;
//import java.util.Hashtable;

/**
 * Class KeyBindings - defines the set of keys for interactive scene viewing
 * 					-uses a hash map to bind keys by their keyCode and an associate ID
 * 					-key mapping set to resemble standard character movements in gaming
 * @author Moebot (Brittany Alkire)
 * Date created: 13 Sept, 2011
 */
public class KeyBindings {
	
	private HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();
	
	/////////////////////
	//   Constructor   //
	/////////////////////
	
	public KeyBindings()
	{
		//MOVE_FORWARD
		keyBindings.put("W", 0);
		//MOVE_BACKWARD
		keyBindings.put("S", 1);
		//STRAFE_LEFT
		keyBindings.put("Q",2);
		//STRAFE_RIGHT
		keyBindings.put("E",3);
		//LOOK_UP
		keyBindings.put("Up",4);
		//LOOK_DOWN
		keyBindings.put("Down",5);
		//LOOK_LEFT
		keyBindings.put("A",6);
		//LOOK_RIGHT
		keyBindings.put("D",7);
		//FLY_UP
		keyBindings.put("+",8); // work in progress
		//FLY_DOWN
		keyBindings.put("-",9); // work in progress
		//RESET CAMERA
		keyBindings.put("R", 10);
	}
	
	//////////////////////////////////
	//     Accessors and Mutators   //
	//////////////////////////////////
	
	public HashMap<String, Integer> getBindings()
	{
		return keyBindings;
	}
	
	public void setBindings(HashMap<String, Integer> bindings)
	{
		this.keyBindings = bindings;
	}

}
