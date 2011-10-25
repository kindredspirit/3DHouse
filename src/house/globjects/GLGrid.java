package house.globjects;

import java.awt.Color;
import javax.media.opengl.*;

public class GLGrid extends GLGeometry
{

    // properties
    float minx,maxx,minz,maxz,tic;
    
    // methods
    public GLGrid(float minx, float maxx, float minz, float maxz, float tic)

    {
        this.minx=minx;
        this.maxx=maxx;
        this.minz=minz;
        this.maxz=maxz;
        this.tic=tic;
        this.setColor(Color.white);
        this.setMode(GL.GL_LINES);
        this.setVertices();
    }

    public GLGrid(float minx, float maxx, float minz, float maxz, float tic,
                float red, float green, float blue)
    {
        this(minx,maxx,minz,maxz,tic);
        this.setColor(red,green,blue);
        this.colors=new float[this.vertices.length];
        int j=0;
        for(int i=0;i<this.vertices.length/4;i++)
        {
        	this.colors[j++]=red;
        	this.colors[j++]=green;
        	this.colors[j++]=blue;
        	this.colors[j++]=1.0f;
        }
    }

    public GLGrid(float minx, float maxx, float minz, float maxz, float tic,
                Color aColor)
    {
        this(minx,maxx,minz,maxz,tic);
        this.setColor(aColor);
        float red=this.my_color[0], green=this.my_color[1], blue=this.my_color[2], a=this.my_color[3];
        
        this.colors=new float[this.vertices.length];
        int j=0;
        for(int i=0;i<this.vertices.length/4;i++)
        {
        	this.colors[j++]=red;
        	this.colors[j++]=green;
        	this.colors[j++]=blue;
        	this.colors[j++]=a;
        }
    }
    
    public void setVertices()
    {
    	float incr;

        int rows=Math.round((this.maxz-this.minz)/this.tic);
        int cols=Math.round((this.maxx-this.minx)/this.tic); 

        this.vertices=new float[(rows+cols+2)*8];
        this.normals=new float[(rows+cols+2)*6];
        incr=0.0f;
        int i=0,j=0;
        
        for(int row=0;row<=rows;row++)
        {
        	this.vertices[i++]=this.minx;
        	this.vertices[i++]=0.0f;
        	this.vertices[i++]=this.minz+incr;
        	this.vertices[i++]=1.0f;

        	this.normals[j++]=0.0f;
        	this.normals[j++]=1.0f;
        	this.normals[j++]=0.0f;
        	
        	this.vertices[i++]=this.maxx;
        	this.vertices[i++]=0.0f;
        	this.vertices[i++]=this.minz+incr;
        	this.vertices[i++]=1.0f;
        	
        	this.normals[j++]=0.0f;
        	this.normals[j++]=1.0f;
        	this.normals[j++]=0.0f;
        	
        	incr+=this.tic;
        }
        
        incr=0.0f;
        for(int col=0;col<=cols;col++)
        {
        	this.vertices[i++]=this.minx+incr;
        	this.vertices[i++]=0.0f;
        	this.vertices[i++]=this.minz;
        	this.vertices[i++]=1.0f;
        	
        	this.normals[j++]=0.0f;
        	this.normals[j++]=1.0f;
        	this.normals[j++]=0.0f;
        	
        	this.vertices[i++]=this.minx+incr;
        	this.vertices[i++]=0.0f;
        	this.vertices[i++]=this.maxz;
        	this.vertices[i++]=1.0f;
        	
        	this.normals[j++]=0.0f;
        	this.normals[j++]=1.0f;
        	this.normals[j++]=0.0f;
        	
        	incr+=this.tic;
        }
    }
}

        