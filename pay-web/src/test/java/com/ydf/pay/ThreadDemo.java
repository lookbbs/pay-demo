package com.ydf.pay;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuandongfei
 * @date 2019/5/31
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Bus bus = new Bus();
        new Thread(() -> {
            Thread.currentThread().setName("t11");
            // 写数据
            int[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            bus.write1(data);
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("t21");
            bus.read2(0, 10);
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("t12");
            // 写数据
            int[] data = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
            bus.write3(data);
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("t22");
            bus.read4(10, 20);
        }).start();
    }

    static class Bus {
        private int[] datas = {};
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        Condition condition4 = lock.newCondition();
        private AtomicInteger flag = new AtomicInteger(1);

        public void write1(int[] data) {
            lock.lock();
            try {
                while (flag.get() != 1) {
                    condition1.await();
                }
                write(data);

                flag.compareAndSet(1, 2);
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void write3(int[] data) {
            lock.lock();
            try {
                while (flag.get() != 3) {
                    condition3.await();
                }
                write(data);

                flag.compareAndSet(3, 4);
                condition4.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void read2(int start, int end) {
            lock.lock();
            try {
                while (flag.get() != 2) {
                    condition2.await();
                }
                read(start, end);
                flag.compareAndSet(2, 3);
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void read4(int start, int end) {
            lock.lock();
            try {
                while (flag.get() != 4) {
                    condition4.await();
                }
                read(start, end);
                flag.compareAndSet(4, 1);
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        public void write(int[] data) {
            System.out.println(Thread.currentThread().getName() + " : 开始写数据 - " + data.length);
            int oldLength = datas.length;
            int newLength = datas.length + data.length;
            int[] tmp = new int[newLength];
            for (int i = 0; i < oldLength; i++) {
                tmp[i] = datas[i];
            }
            for (int i = 0; i < data.length; i++) {
                int index = i + datas.length;
                tmp[index] = data[i];
            }
            datas = tmp;
        }


        public void read(int start, int end) {

            System.out.print(Thread.currentThread().getName() + " : ");
            for (int i = 0; i < datas.length; i++) {
                System.out.print(datas[i] + "  ");
            }
            System.out.println();

        }
    }
}
