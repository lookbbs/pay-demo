package com.ydf.pay;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuandongfei
 * @date 2019/5/31
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            Runnable runnable = () -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点1，当前已有：" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程" + ((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了，继续走呀" : "到达，正在等候"));
                    cyclicBarrier.await();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点2，当前已有：" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程" + ((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了，继续走呀" : "到达，正在等候"));
                    cyclicBarrier.await();

                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点3，当前已有：" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程" + ((cyclicBarrier.getNumberWaiting() == 2) ? "都到齐了，继续走呀" : "到达，正在等候"));
                    cyclicBarrier.await();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(runnable);
        }
        executorService.shutdown();
    }
}
