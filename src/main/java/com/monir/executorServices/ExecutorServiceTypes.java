package com.monir.executorServices;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTypes {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceTypes.class);

    public static void main(String[] args) {
//        execute(Executors.newSingleThreadExecutor(), 3);
//        execute(Executors.newFixedThreadPool(5), 20);
//        execute(Executors.newCachedThreadPool(), 200);
//        execute(Executors.newVirtualThreadPerTaskExecutor(), 2000);  //only way to create virtual thread using executorService
        scheduled();
    }

    private static void scheduled(){
        try(var executorService = Executors.newSingleThreadScheduledExecutor()){
            executorService.scheduleAtFixedRate(()-> {
                log.info("executing task");
            },20,5, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(30));
        }
    }

    private static void execute(ExecutorService executorService, int taskCount) {
        try (executorService) {
            for (int i = 0; i < taskCount; i++) {
                int j = i;
                executorService.submit(() -> task(j));
            }
        }
    }
    private static void task(int i){
        log.info("Task started: {}. Thread info {}",i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("Task ended: {}. Thread info {}",i, Thread.currentThread());
    }
}
