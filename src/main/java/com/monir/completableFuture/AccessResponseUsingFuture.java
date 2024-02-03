package com.monir.completableFuture;

import com.monir.executorServices.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AccessResponseUsingFuture {
    private static final Logger log = LoggerFactory.getLogger(AccessResponseUsingFuture.class);

    public static void main(String[] args) throws Exception {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
//            var product1 = executor.submit(()-> Client.getProduct(1));
            var product1 = CompletableFuture.supplyAsync(()-> Client.getProduct(1),executor);
//            var product1 = CompletableFuture.runAsync(()-> Client.getProduct(1),executor);
            /*
            * above, runAsync will not work. Instead we have to use supplyAsync
            * */
            var product2 = CompletableFuture.supplyAsync(()-> Client.getProduct(50))
                    .exceptionally(ex-> "Product Not found!")
                    .orTimeout(750, TimeUnit.MILLISECONDS)
                    .exceptionally(ex-> "request Timed out!");
            var product3 = CompletableFuture.supplyAsync(()-> Client.getProduct(3));
            var rating = CompletableFuture.supplyAsync(()-> Client.getRating(500))
                    .exceptionally(ex-> -1);
            /*
            * Need to run jar to get product1, product2, product3 response
            * */

            log.info("product-1 {}", product1.get());
            log.info("product-2 {}", product2.get());
            log.info("product-3 {}", product3.get());
            log.info("rating {}", rating.get());
        }
    }
}
