package com.sand.zk.ch5.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class SetDataSample {
	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorHelper.newCuratorFramework();
		client.start();
		String path = "/zk-book/c1";
		client.create().creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL).forPath(path);
		client.setData().forPath(path, "init".getBytes());
		byte[] data = client.getData().forPath(path);
		System.out.println(new String(data));
		client.setData().forPath(path,"123".getBytes());
		data = client.getData().forPath(path);
		System.out.println(new String(data));
		Thread.sleep(Integer.MAX_VALUE);
	}
}
