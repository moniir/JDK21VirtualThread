package com.monir.threadFactory;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryInsteadOFThreadDOTBuilder {
    private static final Logger log = LoggerFactory.getLogger(ThreadFactoryInsteadOFThreadDOTBuilder.class);

    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("monir",1).factory());
        CommonUtils.sleep(Duration.ofSeconds(3));
    }
    /*
        Create few threads
        Each thread creates 1 child thread
        It is a simple demo. In the real life, lets use ExecutorService etc
        Virtual threads are cheap to create.
     */
    private static void demo(ThreadFactory factory){
        for (int i = 0; i < 3; i++) {
            factory.newThread(()->{
                log.info("Task started {}",Thread.currentThread());
                factory.newThread(()->{
                    log.info("child Task started {}",Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("child Task ended {}",Thread.currentThread());

                }).start();
            }).start();
        }
    }
}
