package com.sand.zk.ch5.curator.scene;

import com.sand.zk.ch5.curator.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * master选举 对集群中的应用选择一个master来处理写请求或者核心的业务
 * 计算成功后将结果存进内存数据库供并通知其他客户端使用
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 1:18
 * @since : ${VERSION}
 */
public class MasterSelector {
    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book/master";
        final String host = "192.168.1.2";
        CuratorFramework client = CuratorHelper.newCuratorFramework();
        client.start();
        LeaderSelector selector = new LeaderSelector(client, path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println(host+"->成为master");
                Thread.sleep(3000);
                System.out.println(host+"->完成master逻辑,释放master权利");
            }
        });
        //selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
