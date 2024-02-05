package com.monir.completableFuture;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class CFanyOf {
    private static final Logger log = LoggerFactory.getLogger(CFanyOf.class);

    public static void main(String[] args) {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var cFuture1 = getBDAirlines(executor);
            var cFuture2 = getUSBanglaAirlines(executor);
            log.info("airfare {}", CompletableFuture.anyOf(cFuture1,cFuture2).join());
        }
    }
    private static CompletableFuture<String> getUSBanglaAirlines(ExecutorService executor){
        return CompletableFuture.supplyAsync(()->{
            int random = ThreadLocalRandom.current().nextInt(100,1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "US Bangla $"+random;
        },executor);
    }
    private static CompletableFuture<String> getBDAirlines(ExecutorService executor){
        return CompletableFuture.supplyAsync(()->{
            int random = ThreadLocalRandom.current().nextInt(100,1000);
            CommonUtils.sleep(Duration.ofMillis(random));
            return "Bangladesh Airlines $"+random;
        },executor);
    }
}
