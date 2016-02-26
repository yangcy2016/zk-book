package com.sand.zk.ch5;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZKCreateAPIASync extends AbstractZKUsage {
	public static void main(String[] args) throws IOException, KeeperException,
			InterruptedException {
		ZooKeeper zooKeeper = new ZKCreateAPIASync().zooKeeper();
		zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
				new IStringCallback(), "I am context.");
		zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
				new IStringCallback(), "I am context.");
		Thread.sleep(Integer.MAX_VALUE);
	}

	static class IStringCallback implements AsyncCallback.StringCallback {

		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			System.out.println("Create path result [ " + rc + "," + path + ","
					+ ctx + ",real path name:" + name);
		}

	}
}
