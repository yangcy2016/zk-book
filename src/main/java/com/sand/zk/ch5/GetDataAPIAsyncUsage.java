package com.sand.zk.ch5;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class GetDataAPIAsyncUsage extends AbstractZKUsage{
	private static ZooKeeper zk;
	private static Stat stat = new Stat();
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		zk = new GetDataAPIAsyncUsage().zooKeeper();
		String path = "/zk-book";
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.getData(path, true,new IDataCallback(),null);
		zk.setData(path, "123".getBytes(), -1);
		zk.setData(path, "124".getBytes(), -1);
		System.out.println(Thread.currentThread().getName());
		Thread.sleep(Integer.MAX_VALUE);
	}
	@Override
	public void process(WatchedEvent event) {
		if(isSyncConnected(event)){
			if(isNoneEvent(event)){
				connecttedSemaphore.countDown();
			}else if(isNodeDataChanged(event)){
				zk.getData(event.getPath(), true, new IDataCallback(),null);
			}
		}
	}
	
	static class IDataCallback implements AsyncCallback.DataCallback {

		@Override
		public void processResult(int rc, String path, Object ctx, byte[] data,
				Stat stat) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName+"=>"+rc+","+path+","+new String(data));
			System.out.println(threadName+"=>"+stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		}
		
	}

}
