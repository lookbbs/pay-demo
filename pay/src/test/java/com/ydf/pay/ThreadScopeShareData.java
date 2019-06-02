package com.ydf.pay;

import java.util.Random;

/**
 * @author yuandongfei
 * @date 2019/5/29
 */
public class ThreadScopeShareData {

//    private static int data = 0;
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " has put data : " + data);
                    threadLocal.set(data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public int get() {
            System.out.println("A from " + Thread.currentThread().getName() + " has put data : " + threadLocal.get());
            return 1;
        }
    }

    static class B {
        public int get() {
            System.out.println("B from " + Thread.currentThread().getName() + " has put data : " + threadLocal.get());
            return 1;
        }
    }
}
