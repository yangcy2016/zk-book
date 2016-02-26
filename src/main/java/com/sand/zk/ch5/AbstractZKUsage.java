package com.sand.zk.ch5;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class AbstractZKUsage implements Watcher{
	protected static CountDownLatch connecttedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watch event:"+event);
		if(KeeperState.SyncConnected==event.getState()){
			connecttedSemaphore.countDown();
		}
	}
	
	protected boolean isSyncConnected(WatchedEvent event){
		return KeeperState.SyncConnected == event.getState();
	}
	
	protected boolean isNoneEvent(WatchedEvent event){
		return EventType.None==event.getType()&&null==event.getPath();
	}
	
	protected boolean isNodeDataChanged(WatchedEvent event){
		return EventType.NodeDataChanged==event.getType();
	}
	
	protected boolean isNodeCreated(WatchedEvent event){
		return EventType.NodeCreated==event.getType();
	}
	
	protected boolean isNodeDeleted(WatchedEvent event){
		return EventType.NodeDeleted==event.getType();
	}
	
	public ZooKeeper zooKeeper() throws IOException{
		
		ZooKeeper zooKeeper = new ZooKeeper(
				ConnectString.CONNECT_STRING, 500, this);
		System.out.println(zooKeeper.getState());
		try {
			connecttedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return zooKeeper;
	}
}
