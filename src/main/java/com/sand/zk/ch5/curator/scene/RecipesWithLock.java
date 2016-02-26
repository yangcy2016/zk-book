package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 2:02
 * @since : ${VERSION}
 */
public class RecipesWithLock {
    public static void main(String[] args) {
        CountDownLatch down = new CountDownLatch(1);
        String path = "/zk-book/exclusive_lock";
        CuratorFramework client = CuratorHelper.newCuratorFramework();
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client,path);
        for(int i =0;i<30;i++){
            new Thread(()->{
                try {
                    down.await();
                    lock.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss|SSS");
                String orderNo = format.format(new Date());
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("生产的订单号:"+orderNo);
            }).start();
        }
        down.countDown();
    }
}
