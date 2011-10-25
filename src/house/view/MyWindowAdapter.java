package house.view;

/**
 * @author mbranton
 *
 * Created on Jun 27, 2004
 * Handles only window closing
 * 
 */

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class MyWindowAdapter extends WindowAdapter
{
		OGLapp myParent;
		
		public MyWindowAdapter(OGLapp myParent)
		{
			this.myParent=myParent;
		}
		
		public void windowClosing(WindowEvent e) 
		{
			myParent.windowWillClose();
			System.exit(0);
		}
}
