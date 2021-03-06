package com.zl.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zl.abstracts.AJob;
import com.zl.abstracts.AService;
import com.zl.interfaces.IDispatchJobService;
import com.zl.jobs.WebCrawlingJob;
import com.zl.managers.JobManager;
import com.zl.server.nodes.SlaveNode;

@Service
@Scope("prototype")
public class DispatchJobService extends AService implements IDispatchJobService {

	@Autowired
	public JobManager jobManager;
	
	private SlaveNode slave;
	private AJob job;
	
	/**
	 * disable async service since 
	 * 1. the communication between servers should be fast
	 * 2. now each request contains only a single job. 
	 * If async, there can be some overhead for the communication (this can be improved by put more job info in a single request) 
	 */
	@Async
	@Override
	public void dispatchJob(SlaveNode slave, AJob job) {
		this.slave = slave;
		this.job = job;
		this.start();
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public String getUri() {
		return "/addslavejob";
	}

	@Override
	public String constructRequestUrl() {
		return slave.getDomain() + this.getUri();
	}

	@Override
	public HttpEntity<String> constructRequestHttpEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		JSONObject item = new JSONObject();
		item.put("type", "webcrawling");
		item.put("url", ((WebCrawlingJob)job).getUrl().toString());
		item.put("depth", Integer.valueOf(((WebCrawlingJob)job).getDepth()));
		return new HttpEntity<String>(item.toString(), header);
	}

	@Override
	public void onSuccess(ResponseEntity<String> response) {
		jobManager.removeJobFromRunningStatus(this.job);
	}

	@Override
	public void onFailure(ResponseEntity<String> response) {
		jobManager.moveJobToWaitingStatus(this.job);
	}
}
