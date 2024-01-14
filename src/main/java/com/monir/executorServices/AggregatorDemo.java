package com.monir.executorServices;

import com.monir.executorServices.aggregator.AggregatorService;
import com.monir.executorServices.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(AutoClosableExecService.class);

    public static void main(String[] args) throws Exception {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);
        var future = IntStream.rangeClosed(1,50)
                .mapToObj(id-> executor.submit(()->aggregator.getProductDto(id))).toList();
        //log.info("product -1: {}",aggregator.getProductDto(1));
       var list = future.stream().map(AggregatorDemo::toProductDto).toList();
        log.info("list: {}",list);
    }
    private static ProductDto toProductDto(Future<ProductDto> productDtoFuture) {
        try{
            return productDtoFuture.get();
        } catch (Exception e )  {
            throw new RuntimeException(e);
        }
    }
}
