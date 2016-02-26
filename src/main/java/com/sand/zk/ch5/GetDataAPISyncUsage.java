package com.sand.zk.ch5;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class GetDataAPISyncUsage extends AbstractZKUsage{
	private static ZooKeeper zk;
	private static Stat stat = new Stat();
	
	static{
		System.out.println("init...");
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		zk = new GetDataAPISyncUsage().zooKeeper();
		String path = "/zk-book";
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(new String(zk.getData(path, true, stat)));
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		zk.setData(path, "123".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		if(isSyncConnected(event)){
			if(isNoneEvent(event)){
				connecttedSemaphore.countDown();
			}else if(isNodeDataChanged(event)){
				try {
					System.out.println(new String(zk.getData(event.getPath(), true, stat)));
					System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
