package house.globjects;
/**
 * @author mbranton
 *
 * created: 26 July, 2011
 * 
 * purpose: base class for implementing "GL" aspects of geometry
 * 
 * Revised: 31 July, 2011
 * 			Added material properties for color
 * 			Added display method to account for additional attributes
 * 
 * Revised: 12 Aug, 2011
 * 			Added texture coordinates
 * 			Changed display to accept a ShaderControl object, rather
 * 			than just the attribute ids. Attribute ids are now handled 
 * 			locally within the display method.
 * 
 */


import house.buffers_and_shaders.ShaderControl;
import house.buffers_and_shaders.VBO;
import house.math.Geometry;

import java.awt.Color;
import javax.media.opengl.*;

import com.jogamp.opengl.util.GLBuffers;


public abstract class GLGeometry extends Geometry 
{
	/////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
	//	drawing mode; default to triangle_fan.
	//	some other possibilities are GL.GL_LINES
	//	or GL.GL_POINTS, etc
	int	mode;
	
	// color of the object
	float[] my_color;
    protected float[] colors;
    
    // material properties
    float[] ambient;
    
    float[] diffuse;
    
    float[] specular;
    
    float shininess;
    
    // texture coordinates
    protected float[] texcoordinates;
    
	// and a vertex buffer object for the vertices
	VBO myVBO;
	
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
	
	public GLGeometry()
	{
		// default display mode
		this.setMode(GL.GL_POINTS);
		
		// default color
		this.my_color=new float[4];
		this.setColor(Color.white);
		
		this.vertices=null;
		this.normals=null;
		this.colors=null;
		this.texcoordinates=null;
	}
	
	public void setVBO(GL2 gl)
	{
		myVBO=new VBO();
		if(this.normals==null)
		{
			myVBO.init(gl, this.vertices);
		}
		else if(this.colors==null)
		{
			myVBO.init(gl,this.vertices,this.normals);
		}
		else if(this.texcoordinates==null)
		{
			myVBO.init(gl,this.vertices,this.normals,this.colors);
		}
		else
		{
			myVBO.init(gl,this.vertices,this.normals,this.colors,this.texcoordinates);
		}
	}
	
	public void setMode(int mode)
	{
		this.mode=mode;
	}
		
	public int getMode()
	{
		return this.mode;
	}
	
	private float[] getColorComponants(Color c)
	{
		float[] cf={c.getRed()/(255.0f),c.getGreen()/(255.0f),c.getBlue()/(255.0f),c.getTransparency()};
		return cf;
	}
	
	public void setColor(Color aColor)
    {
    	this.my_color=this.getColorComponants(aColor);
    }
    
    public void setColor(float red, float green, float blue)
    {
    	this.my_color[0]=red;
    	this.my_color[1]=green;
    	this.my_color[2]=blue;
    	this.my_color[3]=1.0f;
    }
    
    public void setColor(float red, float green, float blue, float a)
    {
    	this.my_color[0]=red;
    	this.my_color[1]=green;
    	this.my_color[2]=blue;
    	this.my_color[3]=a;
    }
    
    public void setColors(float[] colors)
    {
    	this.colors=colors;
    }
    
    public float[] getColor()
    {
    	return this.my_color;
    }
    
    public float[] getColors()
    {
    	return this.colors;
    }
    
    public void setAmbient(Color aColor)
    {
    	this.ambient=this.getColorComponants(aColor);
    }
    
    public void setAmbient(float red, float green, float blue, float a)
    {
    	this.ambient[0]=red;
    	this.ambient[1]=green;
    	this.ambient[2]=blue;
    	this.ambient[3]=a;
    }
    
    public float[] getAmbient()
    {
    	return this.ambient;
    }
    
    public void setDiffuse(Color dColor)
    {
    	this.diffuse=this.getColorComponants(dColor);
    }
    
    public void setDiffuse(float red, float green, float blue, float a)
    {
    	this.diffuse[0]=red;
    	this.diffuse[1]=green;
    	this.diffuse[2]=blue;
    	this.diffuse[3]=a;
    }
    
    public float[] getDiffuse()
    {
    	return this.diffuse;
    }
    
    public void setSpecular(Color sColor)
    {
    	this.specular=this.getColorComponants(sColor);
    }
    
    public void setSpecular(float red, float green, float blue, float a)
    {
    	this.specular[0]=red;
    	this.specular[1]=green;
    	this.specular[2]=blue;
    	this.specular[3]=a;
    }
    
    public float[] getSpecular()
    {
    	return this.specular;
    }
    
    public void setShininess(float s)
    {
    	this.shininess=s;
    }
    
    public float getShininess()
    {
    	return this.shininess;
    }

    // draws the geometry
    // input: GL3 context  gl
	public void display(GL2 gl)
	{
		// use this VBO
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, myVBO.vertexBufferObjectID);
		
		// first parameter is shader input variable id 
        gl.glEnableVertexAttribArray(0);
        
        // ditto and associates it to the currently bound VBO
        gl.glVertexAttribPointer(0, 4, GL.GL_FLOAT, false, 0, 0); 
        
        // draw
        gl.glDrawArrays(this.getMode(), 0, this.vertices.length/4);
        
        // disable
        gl.glDisableVertexAttribArray(0);
	}
	
	// draws the geometry
	// input: GL3 context gl and a ShaderControl shader
	public void display(GL2 gl, ShaderControl shader)
	{
		long offset=0;
		int attloc=0;
		
		int[] attid = new int[shader.attribute.size()];
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, myVBO.vertexBufferObjectID);
		
		// first the vertices
		if(myVBO.vertexDataBuffer!=null && shader.attribute.containsKey("vPosition"))
		{
			attid[attloc]=shader.attribute.get("vPosition");
			gl.glVertexAttribPointer(attid[attloc], 4, GL.GL_FLOAT, false, 0, offset);
			gl.glEnableVertexAttribArray(attid[attloc]);
			offset+=GLBuffers.SIZEOF_FLOAT*myVBO.vertexDataBuffer.capacity();
			attloc++;
		}
		
        // then the normals
		if(myVBO.normalDataBuffer!=null && shader.attribute.containsKey("vNormal"))
		{
			attid[attloc]=shader.attribute.get("vNormal");
			gl.glVertexAttribPointer(attid[attloc], 3, GL.GL_FLOAT, false, 0,offset);
			gl.glEnableVertexAttribArray(attid[attloc]);
			offset+=GLBuffers.SIZEOF_FLOAT*myVBO.normalDataBuffer.capacity();
			attloc++;
		}
		
        // then the colors
		if(myVBO.colorDataBuffer!=null && shader.attribute.containsKey("vColor"))
		{
			attid[attloc]=shader.attribute.get("vColor");
			gl.glVertexAttribPointer(attid[attloc], 4, GL.GL_FLOAT, false, 0, offset);
			gl.glEnableVertexAttribArray(attid[attloc]);
			offset+=GLBuffers.SIZEOF_FLOAT*myVBO.colorDataBuffer.capacity();
			attloc++;
		}
        // and the texture map
		if(myVBO.textureDataBuffer!=null && shader.attribute.containsKey("vTexCoord"))
		{
			attid[attloc]=shader.attribute.get("vTexCoord");
			gl.glVertexAttribPointer(attid[attloc], 2, GL.GL_FLOAT, false, 0, offset);
			gl.glEnableVertexAttribArray(attid[attloc]);
			offset+=GLBuffers.SIZEOF_FLOAT*myVBO.textureDataBuffer.capacity();
			attloc++;
		}
		
        // draw it
        gl.glDrawArrays(this.getMode(), 0, this.vertices.length/4);
        
        // done
        for(int i=0;i<attid.length;i++)
        {
        	gl.glDisableVertexAttribArray(attid[i]);
        }  
	}
}