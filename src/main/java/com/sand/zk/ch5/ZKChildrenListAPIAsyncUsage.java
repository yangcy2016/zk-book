package com.sand.zk.ch5;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKChildrenListAPIAsyncUsage extends AbstractZKUsage {
	private static ZooKeeper zk;

	@Override
	public void process(WatchedEvent event) {
		if (isSyncConnected(event)) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				connecttedSemaphore.countDown();
			} else if (EventType.NodeChildrenChanged == event.getType()) {
				try {
					System.out.println("ReGetChildren:"
							+ zk.getChildren(event.getPath(), true));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, KeeperException,
			InterruptedException {
		zk = new ZKChildrenListAPIAsyncUsage().zooKeeper();
		String path = "/zk-book-async";
		zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		zk.getChildren(path, true,new IChildren2Callback(),null);
		zk.create(path + "/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		Thread.sleep(Integer.MAX_VALUE);
	}

	static class IChildren2Callback implements AsyncCallback.Children2Callback {

		public void processResult(int rc, String path, Object ctx,
				List<String> children, Stat stat) {
			System.out.println("Get children node result:[response code:" + rc
					+ ",param path:" + path + ",ctx " + ctx
					+ ",chrildren list:" + children + ",stat:" + stat);
		}

	}
}
