package com.zl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zl.daemons.ThreadPoolDaemon;
import com.zl.interfaces.IJobDispatchDaemon;
import com.zl.interfaces.ISocketListenerDaemon;
import com.zl.utils.SimpleLogger;

@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	public ThreadPoolDaemon threadPoolDaemon;
	
	@Autowired
	public IJobDispatchDaemon jobDispatchDaemon;
	
	@Autowired
	public ISocketListenerDaemon socketListenerDaemon;
	
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		SimpleLogger.info("Application has started");
        startServices();
	}
	
	private void startServices() {
    	SimpleLogger.info("Starting services:");
    	threadPoolDaemon.start();
    	jobDispatchDaemon.start();
    	socketListenerDaemon.startListening();
    }
}