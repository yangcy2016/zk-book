package com.sand.zk.ch5.curator.scene;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : huanghy
 * @create : 2016/2/26 0026 下午 2:33
 * @since : ${VERSION}
 */
public class RecipesCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("选手1")));
        executor.submit(new Thread(new Runner("选手2")));
        executor.submit(new Thread(new Runner("选手3")));
        executor.shutdown();

    }


    public static class Runner implements Runnable{
        private String name;

        public Runner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.err.println(name+"准备好了");
            try {
                barrier.await();
                System.err.println(name+"起跑");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
