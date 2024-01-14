package com.monir.reentrantlock;


import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SynchronizationIO {
    private static final Logger log = LoggerFactory.getLogger(SynchronizationIO.class);
    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

      //  demo(Thread.ofPlatform());
        demo(Thread.ofVirtual()); // issue using synchronization keyword for virtual thread. VT can't unmount
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info("List size {} ",list.size());
    }

    static Runnable runnable = ()->{
      log.info("task started {}",Thread.currentThread());
//        for (int i = 0; i < 50; i++) {
            ioTask();
//        }
        log.info("task ended {}",Thread.currentThread());

    };

    private static synchronized void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(runnable);
        }
    }
    private static synchronized void ioTask(){
        list.add(1);
        CommonUtils.sleep(Duration.ofSeconds(15));
    }
}
