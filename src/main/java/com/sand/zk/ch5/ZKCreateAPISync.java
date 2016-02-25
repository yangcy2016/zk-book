package com.sand.zk.ch5;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZKCreateAPISync implements Watcher{
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public void process(WatchedEvent event) {
		System.out.println("Receive watch event:"+event);
		if(KeeperState.SyncConnected==event.getState()){
			connectedSemaphore.countDown();
		}
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper(
				ConnectString.CONNECT_STRING,
				500,
				new ZKCreateAPISync());
		System.out.println(zooKeeper.getState());
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Success create znode:"+path1);
		String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Success create znode:"+path2);
	}
	
	
}
