package org.stl.hitme.taskMgr.model;

public class RunTask extends Thread {
	public void Terminate()
	{
		this.interrupt();
	}
	
}
