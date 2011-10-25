package house.globjects;
// ////////////////////////////////////////////////////////////////
// Class:           GLTexturedSquare                             //
// Author:          Michael Branton                              //
// Purpose:         Represents a unit square, with corners at    //
//                  (0,0),(0,1),(1,1),(1,0)                      //
//                                                               //
// Date:            Sept 30, 2000                                //
//                                                               //
// ////////////////////////////////////////////////////////////////

public class GLTexturedSquare extends GLTexturedParametricSurface
{
	////////////////////////////////////////////
    //              methods                   //
    ////////////////////////////////////////////
	
    public GLTexturedSquare(int udiv,int vdiv)
    {
        super(udiv,vdiv);
    }
    
    public float x(int u,int v)
    {
        return u/(float)this.udiv;
    }
    
    public float y(int u, int v)
    {
        return v/(float)this.vdiv;
    }
    
    public float z(int u,int v)
    {
        return 0;
    }
}