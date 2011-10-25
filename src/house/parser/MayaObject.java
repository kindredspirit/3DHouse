package house.parser;
import house.globjects.GLGeometry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

import javax.media.opengl.GL;


public class MayaObject extends GLGeometry{
	private Vector<Float> _vertices;
	private Vector<Float> _textureCoords;
	private Vector<Float> _normals;
	private Vector<Integer> _faces;
	//private String lineType = "", strToParse = "", in = "";
	
	//element multipliers
	public static final int VERTEX_MULTIPLIER = 4;
	public static final int TC_MULTIPLIER = 2;  //compute texture coordinates for 2 points when looping
	public static final int NORM_MULTIPLIER = 3;  //computer normal values for 3 points when looping
	public static final int FACE_MULTIPLIER = 12;  //compute face values for 12 faces when looping
	public static final int FACE_JMP = 3;
	public static final int VERTICES_PER_FACE = 6;

	public MayaObject()
	{
		super();
		_vertices = null;
		_textureCoords = null;
		_normals = null;
		_faces = null;
		this.setMode(GL.GL_TRIANGLES);
	}
	
	public void loadObject(String fileName)
	{
		File objFile = new File(fileName);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(objFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fileReader);
		
		_vertices = new Vector<Float>();
		_textureCoords = new Vector<Float>();
		_normals = new Vector<Float>();
		_faces = new Vector<Integer>();
		
		String lineType = "";
		String str = "";
		String in = "";
		
		while(str != null)
		{
			str = null;
			
			try {
				str = br.readLine();
			
				if(str != null && !str.equals(""))
				{
					lineType = str.substring(0,str.indexOf(" "));
					in = str.substring(str.indexOf(" ") + 1, str.length());
					if(lineType.equals("v"))
						this.parseVertexData(in);
					else if(lineType.equals("vt"))
						this.parseTextureData(in);
					else if(lineType.equals("vn"))
						this.parseNormalData(in);
					else if(lineType.equals("f"))
						this.parseFaceData(in);
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
				System.out.println("Could not parse .obj file: " + fileName);
			}
		}
		this.importShit();
	}
	
	public void importShit()
	{
		int numFaces = _faces.size();
		this.vertices = new float[numFaces*VERTEX_MULTIPLIER*VERTICES_PER_FACE];
		this.normals = new float[numFaces*NORM_MULTIPLIER*VERTICES_PER_FACE];
		this.texcoordinates = new float[numFaces*TC_MULTIPLIER*VERTICES_PER_FACE];
		this.colors = new float[vertices.length];
		
		int whichVertex = 0, whichNormal = 0, whichTexture=0;
		
		for(int i = 0; i < _faces.size()/FACE_MULTIPLIER; i++)
		{
			int[] v = {_faces.elementAt(FACE_MULTIPLIER*i)-1, _faces.elementAt(FACE_MULTIPLIER*i+3)-1, _faces.elementAt(FACE_MULTIPLIER*i+6)-1, _faces.elementAt(FACE_MULTIPLIER*i+9)-1};
			int[] n = {_faces.elementAt(FACE_MULTIPLIER*i+1)-1, _faces.elementAt(FACE_MULTIPLIER*i+4)-1, _faces.elementAt(FACE_MULTIPLIER*i+7)-1, _faces.elementAt(FACE_MULTIPLIER*i+10)-1};
			int[] tc = {_faces.elementAt(FACE_MULTIPLIER*i+2)-1, _faces.elementAt(FACE_MULTIPLIER*i+5)-1, _faces.elementAt(FACE_MULTIPLIER*i+8)-1, _faces.elementAt(FACE_MULTIPLIER*i+11)-1};
		
			//VERTICES
			//first triangle
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 3);
			
			this.vertices[whichVertex++] = _vertices.elementAt(v[1]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[1]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[1]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[1]*VERTEX_MULTIPLIER + 3);
			
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 3);
			//second triangle
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[0]*VERTEX_MULTIPLIER + 3);
			
			this.vertices[whichVertex++] = _vertices.elementAt(v[2]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[2]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[2]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[2]*VERTEX_MULTIPLIER + 3);
			
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 1);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 2);
			this.vertices[whichVertex++] = _vertices.elementAt(v[3]*VERTEX_MULTIPLIER + 3);
			
			//NORMALS
			//first triangle
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER + 2);
			
			this.normals[whichNormal++] = _normals.elementAt(n[1]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[1]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[1]*NORM_MULTIPLIER + 2);
			
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER + 2);
			//second triangle
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[0]*NORM_MULTIPLIER + 2);
			
			this.normals[whichNormal++] = _normals.elementAt(n[2]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[2]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[2]*NORM_MULTIPLIER + 2);
			
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER);
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER + 1);
			this.normals[whichNormal++] = _normals.elementAt(n[3]*NORM_MULTIPLIER + 2);
			
			//TEXTURES
			//first triangle
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[0]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[0]*TC_MULTIPLIER + 1);
			
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[1]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[1]*TC_MULTIPLIER + 1);
			
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[2]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[2]*TC_MULTIPLIER + 1);
			
			//second triangle
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[0]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[0]*TC_MULTIPLIER + 1);
			
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[2]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[2]*TC_MULTIPLIER + 1);
			
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[3]*TC_MULTIPLIER);
			this.texcoordinates[whichTexture++] = _textureCoords.elementAt(tc[3]*TC_MULTIPLIER + 1);
		}
	}
	
	/**
	 * parseVertexData() - 
	 * @param theData
	 */
	public void parseVertexData(String theData)
	{
		float[] vertexData = new float[3];
		//elementAt x, y, and z coords of each vertex in obj file
		vertexData[0] = Float.parseFloat(theData.substring(0, theData.indexOf(" ")));
			theData = theData.substring(theData.indexOf(" ") + 1, theData.length());
		vertexData[1] = Float.parseFloat(theData.substring(0, theData.indexOf(" ")));
		vertexData[2] = Float.parseFloat(theData.substring(theData.indexOf(" ") + 1, theData.length()));
		
		//add them to the vector to be passed to the VBO
		_vertices.add(vertexData[0]);   //add x coord
		_vertices.add(vertexData[1]);   //add y coord
		_vertices.add(vertexData[2]);   //add z coord
		_vertices.add(1.0f);
//		System.out.println("Vertex data:");
//		for(int v=0; v < _vertices.size(); v++)
//			System.out.println(_vertices.elementAt(v).toString());
	}
	
	public void parseNormalData(String theData)
	{
		float[] normalData = new float[3];
		//elementAt x, y, and z coords of each vertex normal in obj file
		normalData[0] = Float.parseFloat(theData.substring(0, theData.indexOf(" ")));
			theData = theData.substring(theData.indexOf(" ") + 1, theData.length());
		normalData[1] = Float.parseFloat(theData.substring(0,theData.indexOf(" ")));
		normalData[2] = Float.parseFloat(theData.substring(theData.indexOf(" ") + 1, theData.length()));
		
		//pack it up and send it to the VBO
		_normals.add(normalData[0]);   //add x coord
		_normals.add(normalData[1]);   //add y coord
		_normals.add(normalData[2]);   //add z coord
//		for(int vn=0; vn < _normals.size(); vn++)
//			System.out.println("Normal coordinates:" + " " + _normals.elementAt(vn).toString());
	}
	
	/**
	 * parseTextureData() - organises texture coordinates to map texels to each cooresponding object vertex,
	 * 						then stuffs them into an array and then into a vector to be resized at my leizure.
	 * @param theData - the string of data being passed in from the whatever the buffered reader snatched up
	 */
	public void parseTextureData(String theData)
	{
		float[] textureData = new float[2];
		
		textureData[0] = Float.parseFloat(theData.substring(0, theData.indexOf(" ")));
		textureData[1] = Float.parseFloat(theData.substring(theData.indexOf(" ")+1, theData.length()));
		_textureCoords.add(textureData[0]);
		_textureCoords.add(textureData[1]);
//		for(int vt=0; vt < _textureCoords.size(); vt++)
//			System.out.println("Texture coordinates:" + " " + _textureCoords.elementAt(vt).toString());
	}
	
	/**
	 * parseNormalData() - organises normal coordinates for each vertex from a passed in string of values,
	 *					   then stuffs them into your _FACES (see what i did there?).
	 * @param theData - the parsed data to add to the list of faces
	 */
	public void parseFaceData(String theData)
	{
		String jump = ""; //kinda acts as a look-ahead for the differentiating "/" and ints... and fixes array bounds issues
		
		//parse in segments of three
		for(int f=0; f < 3; f++)
		{
			int[] set = new int[3];
			
			jump = theData.substring(0, theData.indexOf(" ") + 1);
			theData = theData.substring(theData.indexOf(" ") + 1, theData.length());
			
			System.out.println("Jumped from:" + " " + jump);
			_faces.add(Integer.parseInt(jump.substring(0, jump.indexOf("/"))));
				jump = jump.substring(jump.indexOf("/") + 1, jump.length());
			_faces.add(Integer.parseInt(jump.substring(0, jump.indexOf("/"))));
				jump = jump.substring(jump.indexOf("/") + 1, jump.length());
			_faces.add(Integer.parseInt(jump.substring(0,1)));
			System.out.println("f:" + " " + theData);		
		}
		
		_faces.add(Integer.parseInt(theData.substring(0, theData.indexOf("/"))));
		theData = theData.substring(theData.indexOf("/") + 1, theData.length());
		_faces.add(Integer.parseInt(theData.substring(0, theData.indexOf("/"))));
		theData = theData.substring(theData.indexOf("/") + 1, theData.length());
		_faces.add(Integer.parseInt(theData.substring(0,1)));
	}
}
