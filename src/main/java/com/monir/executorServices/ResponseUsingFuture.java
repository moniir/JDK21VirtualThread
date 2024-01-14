package com.monir.executorServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResponseUsingFuture {
    private static final Logger log = LoggerFactory.getLogger(ResponseUsingFuture.class);

    public static void main(String[] args) throws Exception {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            Future<String> future = executor.submit(()-> Client.getProduct(1));
            log.info("product-1 {}",future.get());
        }

    }
}
