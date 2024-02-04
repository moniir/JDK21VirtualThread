package com.monir.completableFuture;

import com.monir.executorServices.AggregatorDemo;
import com.monir.executorServices.aggregator.AggregatorService;
import com.monir.executorServices.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class CompletableFutureAllOf {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureAllOf.class);
    public static void main(String[] args) {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);
        List<CompletableFuture<ProductDto>> futures = IntStream.rangeClosed(1,50)
                .mapToObj(id-> CompletableFuture.supplyAsync(()-> aggregator.getProductDto(id),
                        executor)).toList();

        //wait for all the completable future to complete
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        var list = futures.stream().map(CompletableFuture::join).toList();
        log.info("list {} ", list);
    }
}
