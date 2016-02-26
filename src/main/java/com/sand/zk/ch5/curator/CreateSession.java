package com.sand.zk.ch5.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.sand.zk.ch5.ConnectString;

public class CreateSession {
	public static void main(String[] args) throws InterruptedException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(
				ConnectString.CONNECT_STRING,
				500,
				300,
				retryPolicy);
		
		client.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
}
