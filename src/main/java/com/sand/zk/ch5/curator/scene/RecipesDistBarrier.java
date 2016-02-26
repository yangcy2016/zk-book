package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;

/**
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 2:45
 * @since : ${VERSION}
 */
public class RecipesDistBarrier {
    static DistributedBarrier barrier = null;
    public static void main(String[] args) throws Exception {
        String path = "/zk-book/barrier";

        for(int i = 0;i<30;i++){
            new Thread(()->{
                CuratorFramework client = CuratorHelper.newCuratorFramework();
                client.start();
                barrier = new DistributedBarrier(client,path);
                System.err.println(Thread.currentThread().getName()+"barrier设置");
                try {
                    barrier.setBarrier();
                    barrier.waitOnBarrier();
                    System.err.println("启动...");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();


        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }
}
