package house.buffers_and_shaders;
/**
 * @author mbranton
 * Modified by: Moebot (Brittany Alkire)
 * Created: on July 24, 2011
 * 
 * Purpose: encapsulate creating a vertex buffer object from an 
 * 			array of vertices
 * 
 * Revised: July 31, 2011
 * 			Added normals
 * 
 * 			Aug 12, 2001
 * 			Added colors and texture coordinates
 */

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.*;

import com.jogamp.opengl.util.GLBuffers;

public class VBO
{
    /////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
	public  FloatBuffer vertexDataBuffer=null, 
						normalDataBuffer=null, 
						colorDataBuffer=null, 
						textureDataBuffer=null;
	
	public  int			vertexBufferObjectID;
	
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
	
	// initialize buffer with vertex data
	// input: GL3 context gl, float array of vertex data vertices
	public void init(GL2 gl,float[] vertices)
    {
		// put vertex info into buffer
        this.vertexDataBuffer = GLBuffers.newDirectFloatBuffer(vertices.length);
        this.vertexDataBuffer.put(vertices);
        this.vertexDataBuffer.flip();
        
        // generate (in this case 1) useable buffer ids
        int buffer[] = new int[1];
        gl.glGenBuffers(1, IntBuffer.wrap(buffer));
        
        // assign id for this VBO
        this.vertexBufferObjectID = buffer[0];
        
        // create VBO associated w/ this id
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufferObjectID);
        
        // associate data
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, GLBuffers.SIZEOF_FLOAT *this.vertexDataBuffer.capacity(), this.vertexDataBuffer, GL3.GL_STREAM_DRAW); // associate data
        
        // reset for further binding, if needed
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
    }
	
	// initialize buffer with vertex and normal data
	// input: GL3 context gl, float array of vertex data vertices 
	//        and float array of corresponding normal data normals
	public void init(GL2 gl, float[] vertices, float[] normals)
	{
		// put vertex info into buffer
        this.vertexDataBuffer = GLBuffers.newDirectFloatBuffer(vertices.length);
        this.vertexDataBuffer.put(vertices);
        this.vertexDataBuffer.flip();
        this.normalDataBuffer = GLBuffers.newDirectFloatBuffer(normals.length);
        this.normalDataBuffer.put(normals);
        this.normalDataBuffer.flip();
        
        // generate (in this case 1) useable buffer ids
        int buffer[] = new int[1];
        gl.glGenBuffers(1, IntBuffer.wrap(buffer));
        
        // assign id for this VBO
        this.vertexBufferObjectID = buffer[0];
        
        // create VBO associated w/ this id
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufferObjectID);
        
        // associate data
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, GLBuffers.SIZEOF_FLOAT *(this.vertexDataBuffer.capacity()+this.normalDataBuffer.capacity()), null, GL3.GL_STREAM_DRAW);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,0,GLBuffers.SIZEOF_FLOAT*(this.vertexDataBuffer.capacity()),this.vertexDataBuffer);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,GLBuffers.SIZEOF_FLOAT*this.vertexDataBuffer.capacity(),GLBuffers.SIZEOF_FLOAT*this.normalDataBuffer.capacity(),this.normalDataBuffer);

        // reset for further binding, if needed
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	}
	
	// initialize buffer with vertex, normal and color data
	// input: GL3 context gl, float array of vertex data, vertices, 
	//        float array of corresponding normal data, normals and
	//		  float array of corresponding vertex colors, colors.
	public void init(GL2 gl, float[] vertices, float[] normals, float[] colors)
	{	
		// put vertex info into buffer
        this.vertexDataBuffer = GLBuffers.newDirectFloatBuffer(vertices.length);
        this.vertexDataBuffer.put(vertices);
        this.vertexDataBuffer.flip();
        this.normalDataBuffer = GLBuffers.newDirectFloatBuffer(normals.length);
        this.normalDataBuffer.put(normals);
        this.normalDataBuffer.flip();
        this.colorDataBuffer = GLBuffers.newDirectFloatBuffer(colors.length);
        this.colorDataBuffer.put(colors);
        this.colorDataBuffer.flip();
        
        // generate (in this case 1) useable buffer ids
        int buffer[] = new int[1];
        gl.glGenBuffers(1, IntBuffer.wrap(buffer));
        
        // assign id for this VBO
        this.vertexBufferObjectID = buffer[0];
        
        // create VBO associated w/ this id
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufferObjectID);
        
        // associate data
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, 
        		GLBuffers.SIZEOF_FLOAT *(this.vertexDataBuffer.capacity()+this.normalDataBuffer.capacity()+this.colorDataBuffer.capacity()),
        		null,
        		GL3.GL_STREAM_DRAW);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		0,
        		GLBuffers.SIZEOF_FLOAT*(this.vertexDataBuffer.capacity()),
        		this.vertexDataBuffer);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		GLBuffers.SIZEOF_FLOAT*this.vertexDataBuffer.capacity(),
        		GLBuffers.SIZEOF_FLOAT*this.normalDataBuffer.capacity(),
        		this.normalDataBuffer);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		GLBuffers.SIZEOF_FLOAT*(this.vertexDataBuffer.capacity()+this.normalDataBuffer.capacity()),
        		GLBuffers.SIZEOF_FLOAT*this.colorDataBuffer.capacity(),
        		this.colorDataBuffer);
        
        // reset for further binding, if needed
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	}
	
	// initialize buffer with vertex, normal, color and texture coordinate data
	// input: GL3 context gl, float array of vertex data, vertices, 
	//        float array of corresponding normal data, normals,
	//		  float array of corresponding vertex colors, colors, and
	//		  float array of corrsponding  texture coordinates, textures
	public void init(GL2 gl, float[] vertices, float[] normals, float[] colors, float[] textures)
	{	
		long offset=0;
		
		// put vertex info into buffer
        this.vertexDataBuffer = GLBuffers.newDirectFloatBuffer(vertices.length);
        this.vertexDataBuffer.put(vertices);
        this.vertexDataBuffer.flip();
        this.normalDataBuffer = GLBuffers.newDirectFloatBuffer(normals.length);
        this.normalDataBuffer.put(normals);
        this.normalDataBuffer.flip();
        this.colorDataBuffer = GLBuffers.newDirectFloatBuffer(colors.length);
        this.colorDataBuffer.put(colors);
        this.colorDataBuffer.flip();
        this.textureDataBuffer = GLBuffers.newDirectFloatBuffer(textures.length);
        this.textureDataBuffer.put(textures);
        this.textureDataBuffer.flip();
        
        // generate (in this case 1) useable buffer ids
        int buffer[] = new int[1];
        gl.glGenBuffers(1, IntBuffer.wrap(buffer));
        
        // assign id for this VBO
        this.vertexBufferObjectID = buffer[0];
        
        // create VBO associated w/ this id
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufferObjectID);
        
        // associate data
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, 
        		GLBuffers.SIZEOF_FLOAT *(this.vertexDataBuffer.capacity()+this.normalDataBuffer.capacity()+this.colorDataBuffer.capacity()+this.textureDataBuffer.capacity()),
        		null,
        		GL3.GL_STREAM_DRAW);
        
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		0,
        		GLBuffers.SIZEOF_FLOAT*(this.vertexDataBuffer.capacity()),
        		this.vertexDataBuffer);
        offset+=GLBuffers.SIZEOF_FLOAT*this.vertexDataBuffer.capacity();
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		offset,
        		GLBuffers.SIZEOF_FLOAT*this.normalDataBuffer.capacity(),
        		this.normalDataBuffer);
        offset+=GLBuffers.SIZEOF_FLOAT*this.normalDataBuffer.capacity();
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		offset,
        		GLBuffers.SIZEOF_FLOAT*this.colorDataBuffer.capacity(),
        		this.colorDataBuffer);
        offset+=GLBuffers.SIZEOF_FLOAT*this.colorDataBuffer.capacity();
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,
        		offset,
        		GLBuffers.SIZEOF_FLOAT*this.textureDataBuffer.capacity(),
        		this.textureDataBuffer);
        
        // reset for further binding, if needed
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	}
}
