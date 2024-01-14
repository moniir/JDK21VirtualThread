package com.monir.reentrantlock;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockInsteadOfSynchronization {
    private static final Logger log= LoggerFactory.getLogger(ReentrantLockInsteadOfSynchronization.class);
    private static final Lock lock = new ReentrantLock();
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info("list size {}",list.size());
    }

    static Runnable runnable = ()->{
      log.info("task started", Thread.currentThread());
        for (int i = 0; i < 200; i++) {
            inMemoryTask();
        }
        log.info("Task ended. {}", Thread.currentThread());
    };
    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(runnable);
        }
    }
    private static void inMemoryTask(){
        try {
            lock.lock();
            list.add(1);
        } catch (Exception e){
            log.error("error",e);
        } finally {
            lock.unlock();
        }


    }
}
