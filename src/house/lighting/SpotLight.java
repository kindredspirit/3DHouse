package house.lighting;

/**
 * @author mbranton
 *
 *
 * Class:          "SpotLight"                                       
 * Author:          Michael Branton                              
 * Purpose:         Encapsulates a spotlight, which is a light that 
 * 					has both a direction and a cutoff angle that
 * 					limits the effect of the light. The default 
 * 					direction is straight down (0,-1,0) w/ a cutoff
 * 					of 45 degrees.
 * 
 * Created:         September 30, 2011 
 *
 */

public class SpotLight extends Light 
{

	/////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
	protected float[] direction;
	
	protected float cutoff_angle;
	
	protected float intensity;
	
    /////////////////////////////////////////////
    //              methods                    //
    /////////////////////////////////////////////
	
    public SpotLight()
    {
    	super();
    	
    	this.direction= new float[3];
    	this.direction[0]=0;
    	this.direction[1]=1;
    	this.direction[2]=0;
    	this.setCutoffAngle(45.0f);
    	this.intensity=1.0f;
    }
    
	public void setDirection(float[] direction)
	{
		this.direction=direction;
	}
	
	public float[] getDirection()
	{
		return this.direction;
	}

	public void setCutoffAngle(float angle)
	{
		this.cutoff_angle=angle;
	}
	
	public float getCutoffAngle()
	{
		return this.cutoff_angle;
	}
	
	// this pre-computes the cosine of the cutoff angle, so that
	// the shader doesn't have to do it every time a vertex is 
	// processed
	public float getCutoff()
	{
		return (float)Math.cos((float)(Math.PI*this.cutoff_angle/180.0d));
	}
	
	public void setIntensity(float intensity)
	{
		this.intensity=intensity;
	}
	
	public float getIntensity()
	{
		return this.intensity;
	}
}
