package com.ydf.pay;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuandongfei
 * @date 2019/5/31
 */
public class ThreeConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        final Buss buss = new Buss();
        Thread.currentThread().setName("thread-1");
        new Thread(() -> {
            Thread.currentThread().setName("thread-2");
            for (int i = 0; i < 10; i++) {
                try {
                    buss.sub2(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("thread-3");
            for (int i = 0; i < 10; i++) {
                try {
                    buss.sub3(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            buss.sub1(i + 1);
        }

    }

    static class Buss {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        private int flag = 1;

        public void sub1(int num) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 1) {
                    condition1.await();
                }

                foreach(num, " -- sub1 - ");

                flag = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

        private void foreach(int num, String s) {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + s + num + " : " + i);
            }
        }

        public void sub2(int num) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 2) {
                    condition2.await();
                }

                foreach(num, " -- sub2 - ");

                flag = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }

        public void sub3(int num) throws InterruptedException {
            lock.lock();
            try {
                while (flag != 3) {
                    condition3.await();
                }

                foreach(num, " -- sub3 - ");

                flag = 1;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
