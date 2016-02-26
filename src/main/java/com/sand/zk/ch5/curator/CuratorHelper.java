package com.sand.zk.ch5.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.sand.zk.ch5.ConnectString;

public class CuratorHelper {

	public static CuratorFramework newCuratorFramework() {
		return CuratorFrameworkFactory.builder()
				.connectString(ConnectString.CONNECT_STRING)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.namespace("base").sessionTimeoutMs(500).build();
	}
}
