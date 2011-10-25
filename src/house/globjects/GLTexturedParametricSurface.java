package house.globjects;
// ////////////////////////////////////////////////////////////////
// Class:           TexturedParametricSurface                    //
// Author:          Michael Branton                              //
// Purpose:         Represents a textured surface that can be    //
//                  defined by 3 parametric equations, x(u,v),   //
//                  y(u,v), z(u,v) where (u,v) comes from a      //
//                  point in the unit square.                    //
//                                                               //
// Date:            Sept 30, 2000                                //
//																 //
// Revised:			Aug 17, 2011 to support JOGL 2				 //
//                                                               //
///////////////////////////////////////////////////////////////////

abstract class GLTexturedParametricSurface extends GLTexturedTriangulatedSurface
{   
	////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
	
    GLTexturedParametricSurface(int udiv, int vdiv)
    {
        super(udiv,vdiv);
    }
    
    protected void setVertices(int udiv, int vdiv)
    {   
        for(int v=0;v<vdiv;v++)
        {
            for(int u=0;u<udiv;u++)
            {
                // add lower triangle
                this.addTriangleWithNormal(this.x(u,v),this.y(u,v),this.z(u,v),
                            this.x(u+1,v),this.y(u+1,v),this.z(u+1,v),
                            this.x(u+1,v+1),this.y(u+1,v+1),this.z(u+1,v+1));
                
                // add upper triangle
                this.addTriangleWithNormal(this.x(u,v),this.y(u,v),this.z(u,v),
                            this.x(u+1,v+1),this.y(u+1,v+1),this.z(u+1,v+1),
                            this.x(u,v+1),this.y(u,v+1),this.z(u,v+1));
            }
        }
    }
    
    public  abstract float x(int u, int v);
	
	public  abstract float y(int u, int v);
	
	public  abstract float z(int u, int v);
}