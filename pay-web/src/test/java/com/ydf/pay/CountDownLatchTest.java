package com.ydf.pay;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuandongfei
 * @date 2019/5/31
 */
public class CountDownLatchTest {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch cdOrder = new CountDownLatch(1);
        CountDownLatch cdAnswer = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "正准备接受命令");
                    cdOrder.await();

                    System.out.println("线程：" + Thread.currentThread().getName() + "已接受命令");

                    Thread.sleep((long) (Math.random()*10000));
                    System.out.println("线程：" + Thread.currentThread().getName() + "回应命令处理结束");

                    cdAnswer.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        try {
            Thread.sleep((long) (Math.random()*10000));
            System.out.println();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
