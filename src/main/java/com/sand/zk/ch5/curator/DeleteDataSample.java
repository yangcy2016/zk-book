package com.sand.zk.ch5.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.sand.zk.ch5.ConnectString;

public class DeleteDataSample {
	public static void main(String[] args) throws Exception {
		String path = "/zk-book/c1";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(ConnectString.CONNECT_STRING)
				.sessionTimeoutMs(500)
				.namespace("base")
				.retryPolicy(retryPolicy)
				.build();
		client.start();
		client.create().creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL)
		.forPath(path,"init".getBytes());
		Stat stat = new Stat();
		System.out.println(stat);
		byte[] data = client.getData().storingStatIn(stat).forPath(path);
		System.out.println(new String(data));
		System.out.println(stat);
		client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
