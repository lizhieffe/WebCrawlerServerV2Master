package com.zl;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.zl.daemons.JobDispatchDaemon;
import com.zl.daemons.SocketListenerDaemon;
import com.zl.daemons.ThreadPoolDaemon;
import com.zl.interfaces.IBeanConfiguration;
import com.zl.interfaces.IJobDispatchDaemon;
import com.zl.interfaces.ISocketListenerDaemon;

@Component
@Configuration
public class DaemonConfiguration implements IBeanConfiguration {
	
	public ThreadPoolDaemon createThreadPoolDaemon() {
		return new ThreadPoolDaemon();
	}
	
	public IJobDispatchDaemon createIJobDispatchDaemon() {
		return new JobDispatchDaemon();
	}
	
	public ISocketListenerDaemon createISocketListenerDaemon() {
		return new SocketListenerDaemon();
	}
}
