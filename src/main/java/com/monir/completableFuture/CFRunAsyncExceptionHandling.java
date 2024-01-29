package com.monir.completableFuture;


import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class CFRunAsyncExceptionHandling {
    private static final Logger log = LoggerFactory.getLogger(CFRunAsyncExceptionHandling.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Main started");
        /**
         * If behavior is like main doesn't bother the runAsync() method output but want to assure
         * runAsync() whether complete.
         * */
        runAsync()
                .thenRun(()-> log.info("It is done!"))
                        .exceptionally(ex-> {
                            log.info("error - {}",ex.getMessage());
                            return null;
                        });
        /**
         * whenever ask CF to do something asynchronously, by default CF will use
         * fork-join-pool. We know in runAsync() method, commonUtil.sleep() this is not good because it is blocking operation.
         * so, we like to use virtualThread
         *
         * */
        log.info("Main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }
    private static CompletableFuture<Void> runAsync(){
        log.info("Method started");
       var cfRunAsync = CompletableFuture.runAsync(()->{
            CommonUtils.sleep(Duration.ofSeconds(1));
            throw new RuntimeException("Oops..");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("method ends");
        return cfRunAsync;
    }
}
