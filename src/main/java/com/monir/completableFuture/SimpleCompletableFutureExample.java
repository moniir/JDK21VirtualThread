package com.monir.completableFuture;


import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class SimpleCompletableFutureExample {
    private static final Logger log = LoggerFactory.getLogger(SimpleCompletableFutureExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Main started");
//        var val = fastTask();
//        log.info("value= {}",val.join()); // .join() is blocking
        slowTask().thenAccept(v-> log.info("value = {}",v));  //it is non blocking
        log.info("Main ends");
        CommonUtils.sleep(Duration.ofSeconds(2)); // because VT is a demon thread
    }
    private static CompletableFuture<String> fastTask(){
        log.info("Method started");
        CompletableFuture cf = new CompletableFuture<String>();
        cf.complete("hi");
        log.info("Method ends");
        return cf;
    }
    private static CompletableFuture<String> slowTask(){
        log.info("Method started");
        CompletableFuture cf = new CompletableFuture<String>();
//        cf.complete("hi");
        Thread.ofVirtual().start(()-> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            cf.complete("hi");
        });
        log.info("Method ends");
        return cf;
    }
}
