package com.monir.reentrantlock;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SynchronizationExample {

    private static final Logger log = LoggerFactory.getLogger(SynchronizationExample.class);
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual());

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("list size: {}", list.size());
    }

    static Runnable runnable = () -> {
        log.info("Task started. {}", Thread.currentThread());
        for (int j = 0; j < 200; j++) {
            inMemoryTask();
        }
        log.info("Task ended. {}", Thread.currentThread());
    };

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(runnable);
        }
    }

    private static synchronized void inMemoryTask(){
        list.add(1);
    }

}
