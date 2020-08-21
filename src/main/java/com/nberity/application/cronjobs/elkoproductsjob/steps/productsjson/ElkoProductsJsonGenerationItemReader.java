package com.nberity.application.cronjobs.elkoproductsjob.steps.productsjson;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElkoProductsJsonGenerationItemReader implements ItemReader<ElkoProduct> {

    private List<ElkoProduct> latestInsertedElkoProducts;

    private ElkoProductsJobService elkoProductsJobService;

    private int index;

    @Autowired
    private ElkoProductsJsonGenerationItemReader(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        index = 0;
        System.out.println("Beginning to read products that are available in the stock");
        latestInsertedElkoProducts = elkoProductsJobService.getAllLatestElkoProductsWithAvailableStock();
    }

    @Override
    public ElkoProduct read() {
        if (index < latestInsertedElkoProducts.size()) {
            return latestInsertedElkoProducts.get(index++);
        } else {
            return null;
        }
    }
}
