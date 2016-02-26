package com.sand.zk.ch5;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZKConstructorUsage implements Watcher{
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:"+event);
		if(KeeperState.SyncConnected == event.getState()){
			connectedSemaphore.countDown();
		}
	}
	
	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper(
				"192.168.94.135:2181,192.168.94.135:3181,192.168.94.135:4181", 
				500, 
				new ZKConstructorUsage());
		System.out.println(zooKeeper.getState());
		
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
