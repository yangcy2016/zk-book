package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.CountDownLatch;

/**
 *分布式计数器
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 2:16
 * @since : ${VERSION}
 */
public class RecipesDistAtomicInt {
    public void increment() {
        String path = "/zk-book/distCount";
        CuratorFramework client = CuratorHelper.newCuratorFramework();
        client.start();
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client,path,new RetryNTimes(3,1000));
        try {
            AtomicValue<Integer> ac = atomicInteger.add(1);
            System.err.println("Result:"+ac.preValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch down = new CountDownLatch(1);
        for(int i=0;i<20;i++){
            new Thread(()->{
                try {
                    down.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new RecipesDistAtomicInt().increment();
            }).start();
        }
        down.countDown();
    }
}
