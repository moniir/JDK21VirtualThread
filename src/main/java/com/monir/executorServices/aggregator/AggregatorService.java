package com.monir.executorServices.aggregator;

import com.monir.executorServices.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorService {
    private final ExecutorService executorService;


    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
    public ProductDto getProductDto(int id){
        var product = executorService.submit(()-> Client.getProduct(id));
        var rating = executorService.submit(()-> Client.getRating(id));
        try {
            return new ProductDto(id,product.get(), rating.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
