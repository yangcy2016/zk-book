package com.sand.zk.ch5.curator.scene;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 1:56
 * @since : ${VERSION}
 */
public class RecipesNoLock {
    public static void main(String[] args) {
        CountDownLatch semaphore = new CountDownLatch(1);
        for(int i = 0;i<10;i++){
            new Thread(()->{
                try {
                    semaphore.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss|SSS");
                String orderNo = format.format(new Date());
                System.err.println("生成的订单号:"+orderNo);
            }).start();
        }
        semaphore.countDown();
    }
}
