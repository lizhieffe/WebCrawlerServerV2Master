package com.zl.sockets;

import java.io.IOException;
import java.net.Socket;

import com.zl.abstracts.AJob;
import com.zl.interfaces.ISocketListenerCallback;
import com.zl.managers.JobManager;
import com.zl.utils.SimpleLogger;
import com.zl.utils.SocketUtil;

public class JobListenerCallback implements ISocketListenerCallback {

	private JobManager jobManager;
	
	public JobListenerCallback(JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
	@Override
	public void onReceiveObject(Socket socket, Object obj) {
		try {
			if (obj == null)
				SocketUtil.sendObject(socket, "false");
			else if (obj instanceof AJob) {
				jobManager.addJob((AJob)obj);
				SocketUtil.sendObject(socket, "true");
				SimpleLogger.info("[Socket/Sent]" + obj.toString());
			}
			else if (obj instanceof Verification) {
				SocketUtil.sendObject(socket, "true");
				SimpleLogger.info("[Socket/Sent]" + obj.toString());
			}
		}
		catch (IOException ex) {
			SimpleLogger.error(ex.toString());
		}
	}

}
