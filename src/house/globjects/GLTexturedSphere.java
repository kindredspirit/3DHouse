package house.globjects;
// ////////////////////////////////////////////////////////////////
// Class:           GLTexturedSphere                             //
// Author:          Michael Branton                              //
// Purpose:         Represents a unit sphere,                    //
//                  centered at the origin                       //
//                                                               //
//                                                               //
// Date:            Sept 30, 2000                                //
//                                                               //
// ////////////////////////////////////////////////////////////////

public class GLTexturedSphere extends GLTexturedParametricSurface
{
    private static final double TWO_PI=2*Math.PI;
    
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
    
    public GLTexturedSphere(int udiv,int vdiv)
    {
        super(udiv,vdiv);
    }
    
    public float x(int u,int v)
    {
        return (float)Math.sin(v*Math.PI/this.vdiv)*(float)Math.cos(u*TWO_PI/this.udiv);
    }
    
    public float y(int u, int v)
    {
        return -(float)Math.sin(v*Math.PI/this.vdiv)*(float)Math.sin(u*TWO_PI/this.udiv);
    }
    
    public float z(int u,int v)
    {
        return (float)Math.cos(v*Math.PI/this.vdiv);
    }
}