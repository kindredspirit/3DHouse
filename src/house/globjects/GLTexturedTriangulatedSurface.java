package house.globjects;
///////////////////////////////////////////////////////////////////
// Class:           GLTexturedTriangulatedSurface                //
// Author:          Michael Branton                              //
// Purpose:         Extends GLTriangulatedSurface to allow       //
//                  for texturing                                //
//                                                               //
//                                                               //
// Date:            Sept 30, 2000                                //
//                                                               //
// ////////////////////////////////////////////////////////////////


abstract class GLTexturedTriangulatedSurface extends GLTriangulatedSurface
{
	/////////////////////////////////////////////
    //                  properties             //
    /////////////////////////////////////////////
	
    private int my_texture_function;
    
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
    
    public GLTexturedTriangulatedSurface()
    {
    		super();
            this.texcoordinates=null;
    }
    
    public GLTexturedTriangulatedSurface(int udiv,int vdiv)
    {
        super(udiv,vdiv);
        this.setTexcoords();

    }

    public void setTexcoords()
    {
        float uincr,vincr;
        int current;
        
        this.texcoordinates=new float[2*this.getSize()];
        uincr=1.0f/(float)this.udiv;
        vincr=1.0f/(float)this.vdiv;
        current=0;
        for(int v=0;v<this.vdiv;v++)
        {
            for(int u=0;u<this.udiv;u++)
            {
                this.texcoordinates[current++]=u*uincr;
                this.texcoordinates[current++]=v*vincr;
                
                this.texcoordinates[current++]=(u+1)*uincr;
                this.texcoordinates[current++]=v*vincr;
                
                this.texcoordinates[current++]=(u+1)*uincr;
                this.texcoordinates[current++]=(v+1)*vincr;
                
                this.texcoordinates[current++]=u*uincr;
                this.texcoordinates[current++]=v*vincr;
                
                this.texcoordinates[current++]=(u+1)*uincr;
                this.texcoordinates[current++]=(v+1)*vincr;

                this.texcoordinates[current++]=u*uincr;
                this.texcoordinates[current++]=(v+1)*vincr;
            }
        }
    }
    
    public float[] getTexcoords()
    {
    	return this.texcoordinates;
    }
    
    public void printTexcoordinates()
    {
    	int j=0;
    	for(int i=0;i<this.texcoordinates.length/2;i++)
    	{
    		System.out.println(i+": ("+this.texcoordinates[j++]+","+this.texcoordinates[j++]+")");
    	}
    }
}