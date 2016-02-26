package com.sand.zk.ch5;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ExsitAPISyncUsage extends AbstractZKUsage{

	private static ZooKeeper zk;
	@Override
	public void process(WatchedEvent event) {
		try {
			if(isSyncConnected(event)){
				if(isNoneEvent(event)){
					connecttedSemaphore.countDown();
				}
				else if(isNodeCreated(event)){
					System.out.println("Node("+event.getPath()+")Created");
					zk.exists(event.getPath(), true);
				}
				else if(isNodeDeleted(event)){
					System.out.println("Node("+event.getPath()+")Deleted");
					zk.exists(event.getPath(), true);
				}
				else if(isNodeDataChanged(event)){
					System.out.println("Node("+event.getPath()+")DataChanged");
					zk.exists(event.getPath(), true);
				}
			}
		} catch (Exception e) {
			
		}
		
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		zk = new ExsitAPISyncUsage().zooKeeper();
		String path = "/zk-book";
		zk.exists(path, true);
		zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData(path, "123".getBytes(), -1);
		zk.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete(path+"/c1", -1);
		zk.delete(path, -1);
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
