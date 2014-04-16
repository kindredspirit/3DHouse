package house.view;
import java.awt.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;

/**
 * Author: Moebot (Brittany Alkire) 
 **/
public abstract class OGLapp implements  GLEventListener
{ 
	
	/////////////////////////////////////////////
	//                  properties             //
	/////////////////////////////////////////////

	protected GLProfile glp;
	
	protected GLCapabilities caps; 				//	controls the capabilities of our 
										//	3D rendering context:  color-depth, 
										//	depth-buffering, double-buffering, etc
	protected GLCanvas myCanvas;					//	a GLDrawble context that supports 
										//	hardware accelerationL
	protected Frame myFrame;						//	the frame for our spplication. 
										//	contains the GLCanvas

	protected FPSAnimator myAnimator;				//  provides threading for animation

	protected MyWindowAdapter myAdapter;			//	handles relevant window events, 
										//	like window closing
	
    // default color depth
    int COLOR_BITS=24;
    
    // default depth bits
    int DEPTH_BITS=12;
    
    // default window size
    int WIDTH=750;
    int HEIGHT=450;
    
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
    
	public abstract void windowWillClose();
    
	public void displayChanged(GLAutoDrawable drawable,boolean modeChanged,boolean deviceChanged) 
	{
	}
	
	public void dispose(GLAutoDrawable drawable)
	{
	}
}
