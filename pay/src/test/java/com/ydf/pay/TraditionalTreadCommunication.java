package com.ydf.pay;

/**
 * @author yuandongfei
 * @date 2019/5/29
 */
public class TraditionalTreadCommunication {
    public static void main(String[] args) {
        final Business business = new Business();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                business.sub(i);
            }
        }).start();
        for (int i = 1; i <= 10; i++) {
            business.main(i);
        }
    }

    static class Business {
        // 两个线程处理业务的切换标志
        private boolean runSub = true;

        public synchronized void main(int num) {
            while (runSub) {
                try {
                    // 线程等待
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println("main thread seq : " + i + "  num: " + num);
            }
            runSub = true;
            this.notifyAll();
        }

        public synchronized void sub(int num) {
            while (!runSub) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 2; i++) {
                System.out.println("sub thread seq : " + i + "  num: " + num);
            }
            runSub = false;
            this.notifyAll();
        }
    }
}
