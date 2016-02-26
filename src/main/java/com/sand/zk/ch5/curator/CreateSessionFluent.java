package com.sand.zk.ch5.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.sand.zk.ch5.ConnectString;

public class CreateSessionFluent {
	public static void main(String[] args) throws InterruptedException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		
		CuratorFramework client = CuratorFrameworkFactory.builder()
														 .connectString(ConnectString.CONNECT_STRING)
														 .sessionTimeoutMs(500)
														 .retryPolicy(retryPolicy)
														 .namespace("base")
														 .build();
		client.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
}
