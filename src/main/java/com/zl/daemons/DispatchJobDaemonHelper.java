package com.zl.daemons;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zl.abstracts.AJob;
import com.zl.jobs.JobHelper;
import com.zl.jobs.WebCrawlingJob;
import com.zl.managers.SlaveManager;
import com.zl.server.nodes.SlaveNode;
import com.zl.sockets.SocketSender;

@Component
public class DispatchJobDaemonHelper {
	
	@Autowired
	public SlaveManager slaveManager;
	
	@Autowired
	private ApplicationContext appContext;
	
	Map<SlaveNode, SocketSender> connections = new HashMap<SlaveNode, SocketSender>();
	
	synchronized public void dispatchJob(final AJob job) {
		final SlaveNode slave = findSlave(job);	
		if (!isSlaveNodeConnected(slave))
			connectToSlaveNode(slave);
		connections.get(slave).send(job);
	}
	
	synchronized private boolean isSlaveNodeConnected(SlaveNode slave) {
		if (!connections.containsKey(slave))
			return false;
		else
			return true;
	}
	
	synchronized private void connectToSlaveNode(SlaveNode slave) {
		SocketSender socketSender = new SocketSender(slave.getIp(), slave.getSocketPort());
		socketSender.connect();
		connections.put(slave, socketSender);
	}
	
	public static int hashCode(String s) {
		if (s == null)
			return 0;
		int hash = 7;
		char[] chars = s.toCharArray();
		for (char c : chars)
			hash = (hash * 31 + c) % 31;
		return hash;
	}
	
	/**
	 * find the slave to process the job
	 * 
     * @param strs: A list of strings
     * @return: A list of strings
     */
	synchronized private SlaveNode findSlave(AJob job) {
		if (job == null || !(job instanceof WebCrawlingJob))
			return null;
		String domain = JobHelper.getDomainFromUrl(((WebCrawlingJob)job).getUrl().toString());
		int hash = hashCode(domain);
		return slaveManager.getSlave(hash % slaveManager.getNum());
	}
	
}
