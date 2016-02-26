package com.sand.zk.ch5.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import com.sand.zk.Printer;

public class CreateNodeBackground {
	private static CountDownLatch semaphore = new CountDownLatch(2);
	private static ExecutorService excutor = Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) throws Exception {
		String path = "/zk-book/c1";
		CuratorFramework client = CuratorHelper.newCuratorFramework();
		client.start();
		Printer.newInstance().showMessage();
		client.create().creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL)
		.inBackground((aClient,anEvent)->{
			Printer.newInstance().appendTag("event code").appendValue(anEvent.getResultCode())
			.appendTag("event type").appendValue(anEvent.getType())
			.showMessage();
			semaphore.countDown();
		},excutor)
		.forPath(path,"init".getBytes());
		client.create()
		.creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL)
		.inBackground((aClient,anEvent)->{
			Printer.newInstance().appendTag("event code").appendValue(anEvent.getResultCode())
			.appendTag("event type").appendValue(anEvent.getType())
			.showMessage();
			semaphore.countDown();
		})
		.forPath(path);
		semaphore.await();
		excutor.shutdown();
	}
}
