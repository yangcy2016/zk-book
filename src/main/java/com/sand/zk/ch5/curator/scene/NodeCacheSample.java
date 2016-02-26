package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import com.sand.zk.Printer;

/***
 * 对指定的node进行监听
 */
public class NodeCacheSample {
	public static void main(String[] args) throws Exception {
		String path = "/zk-book/nodecache";
		CuratorFramework client = CuratorHelper.newCuratorFramework();
		client.start();
		client.create().creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL)
		.forPath(path,"init".getBytes());
		final NodeCache cache = new NodeCache(client, path,false);
		cache.start(true);
		cache.getListenable().addListener(()->{
			Printer.newInstance().appendTag("Node data update,new data")
			.appendValue(new String(cache.getCurrentData().getData())).showMessage();
		});
		client.setData().forPath(path,"new".getBytes());
		Thread.sleep(1000);
		client.delete().deletingChildrenIfNeeded().forPath(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
