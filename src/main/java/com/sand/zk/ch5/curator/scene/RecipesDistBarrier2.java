package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

/**
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 3:15
 * @since : ${VERSION}
 */
public class RecipesDistBarrier2 {
    public static void main(String[] args) {
        String path = "/zk-book/barrier2";
        for(int i = 0;i<10;i++){
            new Thread(()->{
                CuratorFramework client = CuratorHelper.newCuratorFramework();
                client.start();
                DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client,path,10);
                try {
                    Thread.sleep(Math.round(Math.random() * 3008));
                    System.err.println(Thread.currentThread().getName()+"号barrier进入");
                    barrier.enter();
                    System.err.println("启动");
                    Thread.sleep(Math.round(Math.random() * 3008));
                    barrier.leave();
                    System.err.println(Thread.currentThread().getName()+"号barrier离开");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
