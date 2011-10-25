package house.math;
/**
 * @author mbranton
 *
 *	created: 26 July, 2011
 *
 *	purpose: base class for describing geometry
 *
 *  Revised: July 31, 2011
 *  
 *  		 Added normals
 */

public abstract class Geometry 
{
	/////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
	
	protected float vertices[]; // the vertices of the polygon
	
	
	protected float normals[]; // and the normals
	
	// implement this to load vertex information into the array vertices[]
	protected void setVertices()
	{
		
	}
	
	// implement this to load vertex and normal information into the arrays vertices[] & normals[]
	protected void setVerticesAndNormals()
	{
		
	}
	
	public void printVertices()
	{
		for(int i=0;i<this.vertices.length;i=i+4)
		{
			System.out.println("("+this.vertices[i]+","+this.vertices[i+1]+","+this.vertices[i+2]+","+this.vertices[i+3]+")\n");
		}
	}
	
	public void printNormals()
	{
		for(int i=0;i<this.normals.length;i=i+3)
		{
			System.out.println("("+this.normals[i]+","+this.normals[i+1]+","+this.normals[i+2]+")\n");
		}
	}
	
	public void printVerticesAndNormals()
	{
		int vpos=0, npos=0;
		for(int i=0;i<this.vertices.length/4;i++)
		{
			System.out.println("v "+i+":("+this.vertices[vpos++]+","+this.vertices[vpos++]+","+this.vertices[vpos++]+","+this.vertices[vpos++]+")");
			System.out.println("n "+i+":("+this.normals[npos++]+","+this.normals[npos++]+","+this.normals[npos++]+")");
		}
	}
}
