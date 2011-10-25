package house.globjects;
// ////////////////////////////////////////////////////////////////
// Class:           GLTexturedParabaloid                         //
// Author:          Michael Branton                              //
// Purpose:         Represents a parabolc surface                //
//                  centered at the origin                       //
//                                                               //
// Date:            Sept 30, 2000                                //
// Modified:        12 October, 2000                             //
//                  calculated in polar coordinates so it looks  //
//                  more bowl-like                               //
//                                                               //
// ////////////////////////////////////////////////////////////////

public class GLTexturedParabaloid extends GLTexturedParametricSurface
{
    private static final double TWO_PI=2*Math.PI;
    
    ////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
    
    public GLTexturedParabaloid(int udiv,int vdiv)
    {
        super(udiv,vdiv);
    }
    
    public float x(int u,int v)
    {
        //return u/(float)this.udiv-0.5f ;
        return v*(float)Math.cos(TWO_PI*u/(float)this.udiv)/(float)this.vdiv;
    }
    
    public float y(int u, int v)
    {
        float s,t;

        s=x(u,v);
        t=z(u,v);
        return s*s+t*t;
        
    }
    
    public float z(int u,int v)
    {
        //return v/(float)this.vdiv-0.5f;
        return -v*(float)Math.sin(TWO_PI*u/(float)this.udiv)/(float)this.vdiv;
    }
}