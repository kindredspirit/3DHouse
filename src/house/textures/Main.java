package house.textures;
// ////////////////////////////////////////////////////////////////
// App:             Texture                                     //
// Author:          Michael Branton                              //
// Purpose:         Demonstrate the use of textures with 		 //
//					parametric surfaces and mipmapping  		 //
//                                                               //
// Date:            Sept 24, 1999                                //
//                                                               //
//                                                               //
//Revised:         Aug  06, 2004                           	 	 //
//				   ported to use JOGL							 //
//																 //
//				   Aug 30, 2011									 //
//                 ported to use JOGL 2							 //
///////////////////////////////////////////////////////////////////

import house.buffers_and_shaders.ShaderControl;
import house.globjects.GLGeometry;
import house.globjects.GLTriangulatedSurface;
import house.lighting.Light;
import house.lighting.SpotLight;
import house.listeners.KeyEvents;
import house.listeners.SpinListener;
import house.math.MatrixFactory;
import house.parser.MayaObject;
import house.view.MrCamera;
import house.view.MyWindowAdapter;
import house.view.OGLapp;

import java.awt.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class Main extends OGLapp {
	// ///////////////////////////////////////////
	// properties //
	// ///////////////////////////////////////////

	ShaderControl myShader; // shader


	// thing to texture
	GLTriangulatedSurface mySurface;

	// change viewpoint
	MrCamera myCamera;

	// global light characteristics
	float global_amb_light_color[] = { 0.25f, 0.25f, 0.25f, 0.0f };
	int[]	attids;	
	
	// individual light
	Light myLight;
	
	//spot light
	SpotLight mySpotLight;
	
	float intensity=10.0f;
	// window size
	final int WIDTH = 600;
	final int HEIGHT = 400;

	// let user spin scene w/ mouse
	SpinListener mySpinListener;
	private KeyEvents myKeyEvents;
	private TextureFactory textureFactory;
	private MayaObject myMonitor,  myRoom;
	
	// //////////////////////////////////////////
	// methods //
	// //////////////////////////////////////////

	// Kicks the whole thing off
	public static void main(String argv[]) {
		new Main();
	}

	// initialization
	public Main() {
		// define version of OGL and capabilities
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);

		// enables anti-aliasing
		caps.setNumSamples(2);
		caps.setSampleBuffers(true);
		
		// initialize the frame
		myFrame = new Frame("Ze Texture");
		myFrame.setSize(WIDTH, HEIGHT);
		myFrame.setLayout(new BorderLayout());

		myCanvas = new GLCanvas(caps);
		myCanvas.addGLEventListener(this);

		// navigate view
		// fun with cameras
		myCamera = new MrCamera(Math.PI / 2, Math.PI, .5f);
		myCanvas.addKeyListener(myKeyEvents = new KeyEvents(myCamera));

		//float[] eye = { 0.5f, 0.5f, 1 };

		// spin
		mySpinListener = new SpinListener();
		mySpinListener.contSpin(false);
		mySpinListener.listen(myCanvas);

		myAdapter = new MyWindowAdapter(this);
		myFrame.addWindowListener(myAdapter);

		myFrame.setSize(WIDTH, HEIGHT);
		myFrame.setLayout(new BorderLayout());

		// Add the GLComponent to the Frame and display it
		myFrame.add(myCanvas);
		myFrame.setVisible(true);
		myCanvas.requestFocus();

		// we use the Animator object to run an animation thread
		myAnimator = new FPSAnimator(myCanvas, 60);
		myAnimator.start();
	}

	// Executed exactly once to initialize the associated GLComponent
	/* (non-Javadoc)
	 * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
	 */
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// init texture properties
		textureFactory = new TextureFactory(gl);
		
		
		// create a local light
		myLight = new Light();
		myLight.setAmbient(Color.black);
		myLight.setDiffuse(Color.white);
		myLight.setSpecular(Color.black);
		myLight.setPosition(0.7f, 0.6f, 3.0f, 1.0f);
		
		// create spotlight
        mySpotLight=new SpotLight();
        mySpotLight.setAmbient(Color.black);
        mySpotLight.setDiffuse(Color.blue);
        mySpotLight.setSpecular(Color.black);

        mySpotLight.setPosition(1.0f, 1.5f,-3.0f, 1.0f);
        mySpotLight.setCutoffAngle(30);
        mySpotLight.setIntensity(this.intensity);
		
		myRoom = new MayaObject();
		myRoom.loadObject("./maya_models/simplecube.obj");
		myRoom.setVBO(gl);
		myRoom.setAmbient(Color.white);
		myRoom.setDiffuse(Color.orange);
		myRoom.setSpecular(Color.white);
		myRoom.setShininess(2.0f);
		
		myMonitor = new MayaObject();
		myMonitor.loadObject("./maya_models/monitor.obj");
		myMonitor.setVBO(gl);
		myMonitor.setAmbient(Color.red);
		myMonitor.setDiffuse(Color.gray);
		myMonitor.setSpecular(Color.white);
		myMonitor.setShininess(1.0f);
	
		// load shaders
		myShader = new ShaderControl();
		myShader.vsrc = myShader.loadShaderSrc("v.glsl");
		myShader.fsrc = myShader.loadShaderSrc("f.glsl");
		myShader.init(gl);
		
			//fragment shader properties
			String[] u={"Projection","Model","View","GlobalAmbient",
					"LightPosition","AmbientProduct","DiffuseProduct",
					"SpecularProduct","Shininess","LightDirection",
					"CutoffAngle","LightIntensity"};
			myShader.setUniforms(gl, u);
			
			//vertex shader properties
			String[] a = { "vPosition", "vNormal", "vColor", "vTexCoord" };
			myShader.setAttributes(gl, a);
		
		// make shader active
		myShader.useShader(gl);
		
		//load textures
		textureFactory = new TextureFactory(gl);
		String[] textures = {"bricks","ar","monitor"};
		String[] imageFiles = {"./textures/brick.jpg", "./textures/ar.jpg","./textures/darkgray.jpg"};
		textureFactory.loadTextures(textures, imageFiles);
		
	
		///////////////
		// SPOTLIGHT //
		///////////////
		
		// set spotlight position
		gl.glUniform4fv(myShader.uniform.get("LightPosition").intValue(), 1,
				mySpotLight.getPosition(), 0);
		// set spotlight direction
		gl.glUniform3fv(myShader.uniform.get("LightDirection").intValue(), 1,
				mySpotLight.getDirection(), 0);
		// set spotlight cutoff angle
		gl.glUniform1f(myShader.uniform.get("CutoffAngle").intValue(),
				mySpotLight.getCutoff());
		// set spotlight intensity
		gl.glUniform1f(myShader.uniform.get("LightIntensity").intValue(),
				mySpotLight.getIntensity());
		// and global ambient light
		gl.glUniform4fv(myShader.uniform.get("GlobalAmbient").intValue(), 1,
				MatrixFactory.prod(myRoom.getAmbient(),
						this.global_amb_light_color), 0);

		// set surface material characteristics
				gl.glUniform4fv(myShader.uniform.get("AmbientProduct").intValue(),
		    			1,
		    			MatrixFactory.prod(myRoom.getAmbient(),myLight.getAmbient()),
		    			0);
				gl.glUniform4fv(myShader.uniform.get("DiffuseProduct").intValue(),
		    			1,
		    			MatrixFactory.prod(myRoom.getDiffuse(),myLight.getDiffuse()),
		    			0);
				gl.glUniform4fv(myShader.uniform.get("SpecularProduct").intValue(),
		    			1,
		    			MatrixFactory.prod(myRoom.getSpecular(),myLight.getSpecular()),
		    			0);
				
		//////////////
		// TEXTURES //
		//////////////
		
		// associate texture sampler to texture unit 0
		//gl.glUniform1i(myShader.uniform.get("textures"), 0);

		// Set the background colour when the GLComponent is cleared
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// do depth testing
		gl.glEnable(GL.GL_DEPTH_TEST);
	}

	// Handles resizing of the GLComponent
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();

		// re-size viewport
		gl.glViewport(0, 0, width, height);

		// re-set projection matrix
		gl.glUniformMatrix4fv(myShader.uniform.get("Projection").intValue(), 1,
				true, MatrixFactory.perspective(45.0f, (float) width / height,
						.1f, 110.0f), 0);

		// init model matrix
		gl.glUniformMatrix4fv(myShader.uniform.get("Model").intValue(), 1,
				true, MatrixFactory.identity(), 0);
	}

	// This method handles the painting of the GLComponent
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// Clear the framebuffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// set our view
		gl.glUniformMatrix4fv(myShader.uniform.get("View").intValue(), 1, true,
				MatrixFactory.lookAt(myCamera.eye, myCamera.look,
						myCamera.up), 0);

		// check for user spinning things and update model matrix
		gl.glUniformMatrix4fv(myShader.uniform.get("Model").intValue(), 1,
				false, mySpinListener.getRotMatrix(), 0);

		// set light position
		gl.glUniform4fv(myShader.uniform.get("LightPosition").intValue(),1,mySpotLight.getPosition(),0);

		
        // update intensity
		gl.glUniform1f(myShader.uniform.get("LightIntensity").intValue(),this.intensity);
		
		// update spotlight direction
		gl.glUniform3fv(myShader.uniform.get("LightDirection").intValue(),1,mySpotLight.getDirection(),0);

		// and the cutoff value
		gl.glUniform1f(myShader.uniform.get("CutoffAngle").intValue(),mySpotLight.getCutoff());
		
		
		textureFactory.useTexture("bricks");
		myRoom.display(gl, myShader);
		
		textureFactory.useTexture("monitor");
		myMonitor.display(gl, myShader);
		
	}

	public void windowWillClose() {
		myAnimator.stop();
	}

	  
    public float[] concatv(float[] v1,float[] v2)
    {
    	int l=v1.length+v2.length;
    	float[] v=new float[l];
    	for(int i=0;i<v1.length;i++)
    	{
    		v[i]=v1[i];
    	}
    	for(int i=v1.length;i<l;i++)
    	{
    		v[i]=v2[i-v1.length];
    	}
    	
    	return v;
    	
    }
    
    float[] concat(float f1, float f2)
    {
    	float[] f=new float[2];
    	f[0]=f1;
    	f[1]=f2;
    	return f;
    }
	
}