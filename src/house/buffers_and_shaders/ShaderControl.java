package house.buffers_and_shaders;
/**
 * @author mbranton
 * Modified by: Moebot (Brittany Alkire)
 * based on code from http://webdevtown.com/index.php/forum/14-java-jogl/15-jogl-example-glsl-shader-setup
 *
 * Created: on July 24, 2011
 * 
 * Purpose: encapsulate loading and compiling shaders
 * 
 * Revised: July 31, 2011
 * 			Added attriubte and uniform HashMaps to keep track of ids
 * 
 */

import javax.media.opengl.*;

import java.io.*;
import java.nio.*;
import java.util.*;

public class ShaderControl
{
	
	/////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
	private int     	vertexShaderProgramID;
	private int     	fragmentShaderProgramID;
	public int     		shaderProgramID;
	public  String[] 	vsrc;
	public  String[] 	fsrc;
	public  HashMap<String,Integer> attribute, uniform;

	////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
	
	
	// attempts to initialize shader code that is 
	// part of this object
	// input: GL context
	public void init( GL2 gl )
	{
		try
		{
			attachShaders(gl);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.attribute=null;
		this.uniform=null;
	}

	// read in shader source from a file
	// input:   name - filename for src code
	// returns: array of strings containing code
	public String[] loadShaderSrc( String name )
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			InputStream is = getClass().getResourceAsStream(name);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
				sb.append('\n');
			}
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("Shader is \n" + sb.toString());
		return new String[]
		{ sb.toString() };
	}

	// compile shaders
	// input: GL context
	// assigns: shader program IDs (vertex, fragment and the compiled program)
	private void attachShaders(GL2 gl ) throws Exception
	{
		IntBuffer intBuffer = IntBuffer.allocate(1);
		
		this.vertexShaderProgramID = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		this.fragmentShaderProgramID = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(this.vertexShaderProgramID, 1, vsrc, null, 0);
		gl.glCompileShader(this.vertexShaderProgramID);

		gl.glGetShaderiv(this.vertexShaderProgramID,GL3.GL_COMPILE_STATUS,intBuffer);
		if(intBuffer.get(0)!=1)
		{
			gl.glGetShaderiv(this.vertexShaderProgramID,GL3.GL_INFO_LOG_LENGTH,intBuffer);
			int size = intBuffer.get(0);
			System.err.println("vertex shader compile error: ");
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetShaderInfoLog(this.vertexShaderProgramID, size, intBuffer, byteBuffer);
				for (byte b : byteBuffer.array())
				{
					System.err.print((char) b);
				}
			}
			else
			{
				System.out.println("Unknown");
			}
			System.exit(1);
		}
		
		gl.glShaderSource(this.fragmentShaderProgramID, 1, fsrc, null, 0);
		gl.glCompileShader(this.fragmentShaderProgramID);
		
		gl.glGetShaderiv(this.fragmentShaderProgramID,GL3.GL_COMPILE_STATUS,intBuffer);
		if(intBuffer.get(0)!=1)
		{
			gl.glGetShaderiv(this.fragmentShaderProgramID,GL3.GL_INFO_LOG_LENGTH,intBuffer);
			int size = intBuffer.get(0);
			System.err.println("fragment shader compile error: ");
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetShaderInfoLog(this.fragmentShaderProgramID, size, intBuffer, byteBuffer);
				for (byte b : byteBuffer.array())
				{
					System.err.print((char) b);
				}
			}
			else
			{
				System.out.println("Unknown");
			}
			System.exit(1);
		}
		
		this.shaderProgramID = gl.glCreateProgram();
		//
		gl.glAttachShader(this.shaderProgramID, this.vertexShaderProgramID);
		gl.glAttachShader(this.shaderProgramID, this.fragmentShaderProgramID);
		gl.glLinkProgram(this.shaderProgramID);
		gl.glValidateProgram(this.shaderProgramID);
		
		gl.glGetProgramiv(this.shaderProgramID, GL2.GL_LINK_STATUS, intBuffer);
		if (intBuffer.get(0) != 1)
		{
			gl.glGetProgramiv(this.shaderProgramID, GL2.GL_INFO_LOG_LENGTH, intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(this.shaderProgramID, size, intBuffer, byteBuffer);
				for (byte b : byteBuffer.array())
				{
					System.err.print((char) b);
				}
			}
			else
			{
				System.out.println("Unknown");
			}
			System.exit(1);
		}
	}
	
	// causes gpu to activate this shader
	// input: GL context
	// returns: shader program ID
    public int useShader( GL2 gl )
	{
		gl.glUseProgram(this.shaderProgramID);
		return this.shaderProgramID;
	}

    // causes gpu to deactivate this shader
    // input: GL context
	public void dontUseShader( GL3 gl )
	{
		gl.glUseProgram(0);
	}
	
	public void setAttributes(GL2 gl,String a[])
	{
		if(this.attribute==null)
		{
			this.attribute=new HashMap<String,Integer>();
		}
		
		for(String e:a)
		{
			this.attribute.put(e,gl.glGetAttribLocation(this.shaderProgramID, e));
		}
	}
	
	public void setAttribute(GL3 gl,String a)
	{
		if(this.attribute==null)
		{
			this.attribute=new HashMap<String,Integer>();
		}
		
		this.attribute.put(a,gl.glGetAttribLocation(this.shaderProgramID, a));
	}
	
	public void setUniforms(GL2 gl,String u[])
	{
		if(this.attribute==null)
		{
			this.uniform=new HashMap<String,Integer>();
		}
		
		for(String e:u)
		{
			this.uniform.put(e,gl.glGetUniformLocation(this.shaderProgramID, e));
		}
	}
	
	public void setUniform(GL3 gl,String u)
	{
		if(this.uniform==null)
		{
			this.uniform=new HashMap<String,Integer>();
		}
		
		this.uniform.put(u,gl.glGetUniformLocation(this.shaderProgramID, u));
	}
}
