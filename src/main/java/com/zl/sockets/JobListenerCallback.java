package com.zl.sockets;

import com.zl.abstracts.AJob;
import com.zl.interfaces.ISocketListenerCallback;
import com.zl.managers.JobManager;

public class JobListenerCallback implements ISocketListenerCallback {

	private JobManager jobManager;
	
	public JobListenerCallback(JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
	@Override
	public void onReceiveObject(Object obj) {
		if (obj == null)
			return;
		if (obj instanceof AJob)
			jobManager.addJob((AJob)obj);
	}

}
