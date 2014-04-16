package house.view;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;

import house.math.MatrixFactory;


/**
 * Class Camera - describes a floating eye with an initial position, view direction
 * 				  and the point currently being looked at, rotation
 * @author Michael Branton
 * Modified by Moebot (Brittany Alkire)
 */

public class MrCamera {

    //	 used to calculate view: eyepoint, view direction vector & resulting point looked at
    public double  	phi,theta;                		// view direction angles
    public float	distance;                       // distance moved       
    public double   phi_s,phi_c,theta_s,theta_c;    // sin/cos of angles

    public static float[] 	eye;					// position of viewer's eye
    public float[] 	lookv;							// direction to look in
    public float[] 	look;							// point looked at
    public float[] 	up;								// which way is up?

    
    final double ANGLE_INCR=Math.PI/18;			// increment viewing angles by 10 degrees
    	
    public MrCamera(double thePhi, double theTheta, float theDistance)
    {
    	phi = thePhi;
    	theta = theTheta;
    	distance = theDistance;		
    	
		// where our eye is
		this.eye=new float[3];
        this.eye[0]=2.0f;
        this.eye[1]=3.0f;
        this.eye[2]=25.0f;
        
        // compute spherical coordinates for view vector
        this.phi_s=Math.sin(phi);
        this.phi_c=Math.cos(phi);
        this.theta_c=Math.cos(theta);
        this.theta_s=Math.sin(theta);
        
        // compute view vector
        this.lookv=new float[3];
        this.lookv[0]=(float)(this.phi_s*this.theta_s);
        this.lookv[1]=(float)(this.phi_c);
        this.lookv[2]=(float)(this.phi_s*this.theta_c);

        // compute point looked at
        this.look=new float[3];
        this.look[0]=this.eye[0]+this.lookv[0];
        this.look[1]=this.eye[1]+this.lookv[1];
        this.look[2]=this.eye[2]+this.lookv[2];

        // set up direction
        this.up=new float[3];
        this.up[0] = (float)(-Math.sin(this.theta)*Math.cos(this.phi));
        this.up[1] = (float)Math.sin(this.phi);
        this.up[2] = (float)(-Math.cos(this.theta)*Math.cos(this.phi));
        
    }
    
    /*******************************************************************
     * init() - initialize all class variables for the gl component
     * @param drawable : GLAutoDrawable
     *******************************************************************/
    public void init(GLAutoDrawable drawable)
    {
		GL3 gl = drawable.getGL().getGL3();

        // compute spherical coordinates for view vector
        this.distance=0.5f;
        this.phi=Math.PI/2;
        this.phi_s=Math.sin(phi);
        this.phi_c=Math.cos(phi);
        this.theta=Math.PI;
        this.theta_c=Math.cos(theta);
        this.theta_s=Math.sin(theta);


    }
    
    /**************************************************************************************************
     * lookAt() - kinda like the glu.gluLookAt(), but way better because Branton 
     * 			  wrote it and it's in my code.
     * @return float[] - x, y, z coordinate arrays containing the result of the viewing transformation
     **************************************************************************************************/
    public float[] lookAt()
    {
        return MatrixFactory.lookAt(this.eye,this.look,this.up);
    }
}
