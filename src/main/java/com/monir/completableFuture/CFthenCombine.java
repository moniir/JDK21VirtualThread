package com.monir.completableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class CFthenCombine {
    private static final Logger log = LoggerFactory.getLogger(CFthenCombine.class);
    record AirFare(String name, int amount){}

    public static void main(String[] args) {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getUSBanglaAirlines(executor);
            var cf2 = getBDAirlines(executor);
          var bestDeal = cf1.thenCombine(cf2,(a,b)-> a.amount<= b.amount? a: b)
                    .thenApply(af ->new AirFare(af.name(), (int) (af.amount() * 0.9)))
                  .join();
          log.info("Best deal {}",bestDeal);
        }
    }
    private static CompletableFuture<AirFare> getUSBanglaAirlines(ExecutorService executor){
        return CompletableFuture.supplyAsync(()->{
            int random = ThreadLocalRandom.current().nextInt(100,1000);
            return new AirFare("US Bangla",random);
        },executor);
    }

    private static CompletableFuture<AirFare> getBDAirlines(ExecutorService executor){
        return CompletableFuture.supplyAsync(()->{
            int random = ThreadLocalRandom.current().nextInt(100,1000);
            return new AirFare("Bangladesh airline",random);
        },executor);
    }
}
