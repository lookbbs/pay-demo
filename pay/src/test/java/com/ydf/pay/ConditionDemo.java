package com.ydf.pay;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuandongfei
 * @date 2019/5/31
 */
public class ConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        final Buss buss = new Buss();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    buss.sub1(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            buss.sub2(i + 1);
        }

    }

    static class Buss {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private boolean flag = true;

        public void sub1(int num) throws InterruptedException {
            lock.lock();
            try {
                while (flag) {
                    condition.await();
                }
                for (int i = 0; i < 5; i++) {
                    System.out.println("sub1 - " + num + " -- " + Thread.currentThread().getName() + " : " + i);
                }
                flag = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void sub2(int num) throws InterruptedException {
            lock.lock();
            try {
                while (!flag) {
                    condition.await();
                }
                for (int i = 0; i < 2; i++) {
                    System.out.println("sub2 - " + num + " -- " + Thread.currentThread().getName() + " : " + i);
                }
                flag = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
