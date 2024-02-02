package com.monir.completableFuture;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;


public class CFSupplyAsync {
    private static final Logger log = LoggerFactory.getLogger(CFSupplyAsync.class);

    public static void main(String[] args) {
        log.info("main starts");
        supplyAsync()
                .thenAccept(v-> log.info("value={}",v));
        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }
    private static CompletableFuture<String> supplyAsync(){
        log.info("methods starts");
        var cf = CompletableFuture.supplyAsync(()->{
            CommonUtils.sleep(Duration.ofSeconds(1));
            return "hi";
        }, Executors.newVirtualThreadPerTaskExecutor());

       /* var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(()->{
            CommonUtils.sleep(Duration.ofSeconds(1));
            cf.complete("Hi");
        });*/
        log.info("methods ends");
        return cf;
    }
}
