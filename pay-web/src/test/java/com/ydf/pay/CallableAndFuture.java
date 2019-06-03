package com.ydf.pay;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;

/**
 * @author yuandongfei
 * @date 2019/5/30
 */
public class CallableAndFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,10,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>());

        CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        for (int i = 0; i < 10; i++) {
            int task=i+1;
            completionService.submit(() -> RandomStringUtils.random(5) + "  ---  " + task);
        }

        for (int i = 0; i < 10; i++) {
            String s = completionService.take().get();
            System.out.println(s);
        }
    }
}
