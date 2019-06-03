package com.ydf.pay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yuandongfei
 * @date 2019/5/30
 */
public class CacheDemo {
    public static void main(String[] args) {
        CacheDemo cacheDemo = new CacheDemo();

    }

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Map<String, Object> cache = new HashMap<>();

    public Object get(String key) {
        rwl.readLock().lock();
        try {
            Object o = cache.get(key);
            if (o == null) {
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    // 读取DB数据
                    if(o==null) {
                        o = "abc";
                    }
                }finally {
                    rwl.writeLock().unlock();
                }
            }
            return o;
        } finally {
            rwl.readLock().unlock();
        }
    }

    public void set(String key, Object data) {
        cache.put(key, data);
    }
}
