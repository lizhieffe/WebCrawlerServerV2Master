package com.zl;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.zl.daemons.DispatchJobDaemon;
import com.zl.daemons.ServerSocketListenerDaemon;
import com.zl.daemons.ThreadPoolDaemon;
import com.zl.interfaces.IBeanConfiguration;
import com.zl.interfaces.IJobDispatchDaemon;
import com.zl.interfaces.IServerSocketListenerDaemon;

@Component
@Configuration
public class DaemonConfiguration implements IBeanConfiguration {
	
	public ThreadPoolDaemon createThreadPoolDaemon() {
		return new ThreadPoolDaemon();
	}
	
	public IJobDispatchDaemon createIJobDispatchDaemon() {
		return new DispatchJobDaemon();
	}
	
	public IServerSocketListenerDaemon createIServerSocketListenerDaemon() {
		return new ServerSocketListenerDaemon();
	}
}
