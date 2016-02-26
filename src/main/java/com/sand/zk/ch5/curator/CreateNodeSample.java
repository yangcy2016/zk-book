package com.sand.zk.ch5.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import com.sand.zk.ch5.ConnectString;

public class CreateNodeSample {
	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(ConnectString.CONNECT_STRING)
				.sessionTimeoutMs(500).namespace("base")
				.retryPolicy(retryPolicy).build();
		client.start();
		String path = "/zk-book/c1";
		client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.EPHEMERAL)
				.forPath(path, "init".getBytes());
		Thread.sleep(Integer.MAX_VALUE);
	}
}
