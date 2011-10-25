package house.textures;

import house.parser.MayaObject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3;
import javax.xml.stream.events.Namespace;

import com.jogamp.opengl.util.GLBuffers;

public class TextureFactory {
	

	// texture list
	static ByteBuffer[] textures;
	private int[] textureIndices;
	private String[] names;
	private BufferedImage[] images;
	private GL2 gl;
	
	// texture image
	Image anImage;
	MayaObject myObject;
	
	public TextureFactory(GL2 gl)
	{
		this.gl = gl;
	}
	

    private void loadTextures(GL2 gl2,String image,int loc)
    {
    	File file = new File(image);
    	images[loc] = null;
        int bpp = 3;			// byte per pixel
        
    	try 
		{
    		images[loc] = ImageIO.read(file);
		}
    	catch(IOException e)
		{
    		System.out.println("texture not loaded");
    		e.printStackTrace();
		}
    	
        int[] packedPixels = new int[images[loc].getWidth() * images[loc].getHeight()];

        PixelGrabber pixelgrabber = new PixelGrabber(images[loc], 0, 0, images[loc].getWidth(), images[loc].getHeight(), 
        											packedPixels, 0, images[loc].getWidth());
        try 
        {
            pixelgrabber.grabPixels();
        } 
        catch (InterruptedException e) 
        {
            throw new RuntimeException();
        }
        
        textures[loc] = GLBuffers.newDirectByteBuffer(packedPixels.length * bpp);

        for (int row = images[loc].getHeight() - 1; row >= 0; row--) 
        {
            for (int col = 0; col < images[loc].getWidth(); col++) 
            {
                int packedPixel = packedPixels[row * images[loc].getWidth() + col];
                textures[loc].put((byte) ((packedPixel >> 16) & 0xFF));
                textures[loc].put((byte) ((packedPixel >> 8) & 0xFF));
                textures[loc].put((byte) ((packedPixel >> 0) & 0xFF));
            }
        }

        textures[loc].flip();
        
        // now bind the texture
        gl2.glBindTexture(GL.GL_TEXTURE_2D, textureIndices[loc]);
        gl2.glTexImage2D(GL.GL_TEXTURE_2D,0,GL.GL_RGB,images[loc].getWidth(),images[loc].getHeight(),
                         0,GL.GL_RGB,GL.GL_UNSIGNED_BYTE,textures[loc]);
        
        
        gl2.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );
        gl2.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        
        
        gl2.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
        gl2.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST );

        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        
        // gen mipmaps and replace a pair above with one of the pairs below and 
        // check moire pattern on roman's shirt
             
        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST_MIPMAP_LINEAR);
        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST_MIPMAP_LINEAR);
        
        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
        //gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
               
        //gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
    }
	
    /**
     * Map a single texture to a parametric surface
     * @param theTexture - the texture to map
     * @param theFile - The path to the image file to load it from
     */
	public void loadTexture(String theTexture, String theFile)
	{
		textureIndices = new int[1];
		names = new String[1];
		names[0] = theTexture;
		
		// reserve texture list
        gl.glGenTextures(1,this.textureIndices,0);
        
        // load texture image
        this.loadTextures(gl,theFile,0);
        
        // say which texture unit we're going to use
        gl.glActiveTexture(GL.GL_TEXTURE0);
        
        // use the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndices[0]);
	}
	
	/**
	 * Map multiple textures to a set  of parametric surfaces
	 * @param allTheTextures - the list of textures to map
	 * @param allTheFiles - the list of image files to load
	 */
	public void loadTextures(String[] allTheTextures, String[] allTheFiles)
	{
		names = new String[allTheFiles.length];
		textureIndices = new int[allTheTextures.length];
		images = new BufferedImage[allTheTextures.length];
		textures = new ByteBuffer[allTheTextures.length];
		
		for(int i = 0; i < textureIndices.length; i++)
		{
			names[i] = allTheTextures[i];
			
			//reserve texture list
			gl.glGenTextures(textureIndices.length, this.textureIndices, 0);
			
			//load texture image
			this.loadTextures(gl, allTheFiles[i], i);
			
			//say which texture unit we're going to use
			gl.glActiveTexture(GL.GL_TEXTURE0);
		}
	}
	
	/**
	 * Make texture(s) active and bind them
	 * @param index - the location of a specific texture in the list of textures
	 */
	public void useTexture(int index)
    {	
		// now bind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndices[index]);
        gl.glTexImage2D(GL.GL_TEXTURE_2D,0,GL.GL_RGB,images[index].getWidth(),images[index].getHeight(),
                         0,GL.GL_RGB,GL.GL_UNSIGNED_BYTE,textures[index]);
        
        
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        
        
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST );

        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        
        // gen mipmaps and replace a pair above with one of the pairs below and 
        // check moire pattern on roman's shirt
             
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST_MIPMAP_LINEAR);
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST_MIPMAP_LINEAR);
        
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
               
        gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
    }
	
	public void useTexture(String textureName)
	{
		int index = -1;
		for(int i= 0; i<textureIndices.length; i++)
		{
			if(textureName.equals(names[i]))
			{
				index = i;
				i = textureIndices.length;
			}
		}
		this.useTexture(index);
	}
}