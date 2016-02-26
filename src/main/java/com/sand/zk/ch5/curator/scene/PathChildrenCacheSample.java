package com.sand.zk.ch5.curator.scene;

import com.sand.zk.Printer;
import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.CreateMode;

/**
 * 对指定的子node进行监听
 */
public class PathChildrenCacheSample {
	public static void main(String[] args) throws Exception {
		String path = "/zk-book/childrencache";
		CuratorFramework client = CuratorHelper.newCuratorFramework();
		client.start();
		PathChildrenCache cache = new PathChildrenCache(client,path,true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener((aClient,anEvent)->{
			switch (anEvent.getType()) {
			case CHILD_ADDED:
				Printer.newInstance().appendTag("CHILD_ADDED").appendValue(anEvent.getData().getPath()).showMessage();
				break;
			case CHILD_UPDATED:
				Printer.newInstance().appendTag("CHILD_UPDATED").appendValue(anEvent.getData().getPath()).showMessage();
				break;
			case CHILD_REMOVED:
				Printer.newInstance().appendTag("CHILD_REMOVED").appendValue(anEvent.getData().getPath()).showMessage();
				break;
			default:
				break;
			}
		});
		
		client.create().creatingParentsIfNeeded()
		.withMode(CreateMode.PERSISTENT)
		.forPath(path,"init".getBytes());
		Thread.sleep(1000);
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path+"/c1");
		Thread.sleep(1000);
		client.delete().guaranteed().forPath(path+"/c1");
		Thread.sleep(1000);
		client.delete().guaranteed().forPath(path);
		Thread.sleep(Integer.MAX_VALUE);

	}
}
