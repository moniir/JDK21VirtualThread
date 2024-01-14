package com.monir.reentrantlock;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockInsteadOfSynchronizationIO {
    private static final Logger log= LoggerFactory.getLogger(ReentrantLockInsteadOfSynchronizationIO.class);
    private static final Lock lock = new ReentrantLock();

    static {
        System.setProperty("jdk.tracePinnedThreads","full");
    }

    public static void main(String[] args) {
        Runnable runnable1 = ()-> log.info("***Test Message***");
//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable1);
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    static Runnable runnable = ()->{
      log.info("task started", Thread.currentThread());
//        for (int i = 0; i < 200; i++) {
        ioTask();
//        }
        log.info("Task ended. {}", Thread.currentThread());
    };
    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(runnable);
        }
    }
    private static void ioTask(){
        try {
            lock.lock();
            CommonUtils.sleep(Duration.ofSeconds(15));
        } catch (Exception e){
            log.error("error",e);
        } finally {
            lock.unlock();
        }


    }
}
