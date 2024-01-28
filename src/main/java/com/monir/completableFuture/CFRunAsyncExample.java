package com.monir.completableFuture;


import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class CFRunAsyncExample {
    private static final Logger log = LoggerFactory.getLogger(CFRunAsyncExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Main started");
        runAsync();
        /**
         * whenever ask CF to do something asynchronously, by default CF will use
         * fork-join-pool. We know in runAsync() method, commonUtil.sleep() this is not good because it is blocking operation.
         * so, we like to use virtualThread
         *
         * */
        log.info("Main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }
    private static void runAsync(){
        log.info("Method started");
 /*       CompletableFuture.runAsync(()->{
            CommonUtils.sleep(Duration.ofSeconds(1));
            log.info("Task Completed");
        });*/
        CompletableFuture.runAsync(()->{
            CommonUtils.sleep(Duration.ofSeconds(1));
            log.info("Task Completed");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("method ends");
    }
}
