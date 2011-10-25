package house.lighting;
/**
 * @author mbranton
 *
 *
 * Class:          "Light"                                       
 * Author:          Michael Branton                              
 * Purpose:         Encapsulates a light source                  
 *                  Light colors may be specified as (r,g,b,a)   
 *                  values or as Colors. If Colors are used,     
 *                  it is opaque (i.e. alpha is 1.0f)            
 *                  The number of the light is automatically     
 *                  assigned as the next available light number, 
 *                  beginning with 0;                            
 *                                                               
 *                                                               
 *                                                               
 * Created:         September 30, 1999                           
 * Revised:         October    2, 1999                           
 *                  added automatic light numbering              
 *                                                               
 *                  February 11, 2002                            
 *                  bug fix: in switchOff, check should be for   
 *                           whichever light is being switched   
 *                           off, not light0.                    
 *                           thnx to jjenings for spotting that  
 *                                                               
 *                  February 20, 2002                            
 *                  bug fix: changed r,g,g typo to r,g,b         
 *                           thnx to rsmolkin for spotting that  
 *                           removed light (#) as a parameter to 
 *                           switchOff method                    
 *                  added:   can set color by passing in an      
 *                           array of floats                     
 *                                                               
 *                           lighting gets enabled when a light  
 *                           is switched on
 *                       
 *                  Jul  09, 2004
 * 					ported to use JOGL
 * 
 *                  Aug  05, 2011
 *                  ported to use JOGL 2: removed fixed-pipeline dependencies
 */

import java.awt.Color;

public class Light
{
    /////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
    
    // max color values
    protected static float      MAXRED=   (float)Color.white.getRed(),
                                MAXGREEN= (float)Color.white.getGreen(),
                                MAXBLUE=  (float)Color.white.getBlue();
    
    // light colors
    protected float[]           ambient,
                                diffuse,
                                specular;
                
    // light position            
    protected float[]           position;
    
    
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
    
    public Light()
    {
        this.ambient= new float[4];
        this.diffuse= new float[4];
        this.specular= new float[4];
        
        this.position=new float[4];
        
        // default to white positional light @ (1,1,1)
        this.setAmbient(1.0f,1.0f,1.0f,1.0f);
        this.setDiffuse(1.0f,1.0f,1.0f,1.0f);
        this.setSpecular(0.0f,0.0f,0.0f,0.0f);
        this.setPosition(1.0f,1.0f,1.0f,1.0f);        
    }
    
    public void setAmbient(float r,float g,float b,float a)
    {
        this.ambient[0]=r;
        this.ambient[1]=g;
        this.ambient[2]=b;
        this.ambient[3]=a;
    }
    
    public void setAmbient(Color c)
    {
        this.ambient[0]=c.getRed()/MAXRED;
        this.ambient[1]=c.getGreen()/MAXGREEN;
        this.ambient[2]=c.getBlue()/MAXBLUE;
        this.ambient[3]=1.0f;
    }

    public void setAmbient(float[] c)
    {
        this.ambient[0]=c[0];
        this.ambient[1]=c[1];
        this.ambient[2]=c[2];
        this.ambient[3]=c[3];
    }
    public void setDiffuse(float r,float g,float b,float a)
    {
        this.diffuse[0]=r;
        this.diffuse[1]=g;
        this.diffuse[2]=b;
        this.diffuse[3]=a;
    }

    public void setDiffuse(Color c)
    {
        this.diffuse[0]=c.getRed()/MAXRED;
        this.diffuse[1]=c.getGreen()/MAXGREEN;
        this.diffuse[2]=c.getBlue()/MAXBLUE;
        this.diffuse[3]=1.0f;
    }    

    public void setDiffuse(float[] c)
    {
        this.diffuse[0]=c[0];
        this.diffuse[1]=c[1];
        this.diffuse[2]=c[2];
        this.diffuse[3]=c[3];
    }
    
    public void setSpecular(float r,float g,float b,float a)
    {
        this.specular[0]=r;
        this.specular[1]=g;
        this.specular[2]=b;
        this.specular[3]=a;
    }
    
    public void setSpecular(Color c)
    {
        this.specular[0]=c.getRed()/MAXRED;
        this.specular[1]=c.getGreen()/MAXGREEN;
        this.specular[2]=c.getBlue()/MAXBLUE;
        this.specular[3]=1.0f;
    }

    public void setSpecular(float[] c)
    {
        this.specular[0]=c[0];
        this.specular[1]=c[1];
        this.specular[2]=c[2];
        this.specular[3]=c[3];
    }
    
    public float[] getAmbient()
    {
        return this.ambient;
    }
    
    public float[] getDiffuse()
    {
        return this.diffuse;
    }
    
    public float[] getSpecular()
    {
        return this.specular;
    }
    
    public void setPosition(float x,float y,float z,float w)
    {
        this.position[0]=x;
        this.position[1]=y;
        this.position[2]=z;
        this.position[3]=w;
    }
    
    public void setPosition(float[] p)
    {
        this.position[0]=p[0];
        this.position[1]=p[1];
        this.position[2]=p[2];
        this.position[3]=p[3];
    } 
    
    public float[] getPosition()
    {
        return this.position;
    }   
}