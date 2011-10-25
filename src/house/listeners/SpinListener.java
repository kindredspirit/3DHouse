package house.listeners;
public class SpinListener extends trackball
{
	protected boolean contspin = false;
	
    public SpinListener()
    {
        super();
    }

    // overrides parent; allows for motion without continuous spining, i.e., no inertia
    public float[] getRotMatrix() 
    {
    	float[] rotMat = buildMatrix(this.curQuat);
    	if(this.spin)
    	{
    		curQuat = addQuats(this.lastQuat, this.curQuat);
    		this.spin=this.contspin;
    	}
    	return rotMat;
    }
    
    // if set true, object will continue to spin
    public void contSpin(boolean contspin)
    {
    	this.contspin=contspin;
    }
}