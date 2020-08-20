package com.nberity.application.cronjobs.elkoproductsjob.steps.allproducts;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllElkoProductsItemWriter implements ItemWriter<ElkoProduct> {

    private ElkoProductsJobService elkoProductsJobService;

    private int latestElkoProductsVersionNr;
    private List<ElkoProduct> elkoProducts;

    @Autowired
    private AllElkoProductsItemWriter(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        elkoProducts = new ArrayList<>();
        latestElkoProductsVersionNr = stepExecution.getJobExecution().getExecutionContext().getInt("latestVersionNr");
    }

    @Override
    public void write(List<? extends ElkoProduct> items) {
        System.out.println("Beginning");
        System.out.println("Length: " + items.size());
        long start = System.currentTimeMillis();
        setLatestVersionNrToElkoProducts(items);
        elkoProductsJobService.saveAllElkoProducts((List<ElkoProduct>) items);
        elkoProducts.addAll(items);
        long finish = System.currentTimeMillis();
        System.out.println("Time elapsed: " + ((finish - start) / 1000));
    }

    private void setLatestVersionNrToElkoProducts(List<? extends ElkoProduct> items) {
        for (ElkoProduct product : items) {
            product.setVersionNr(latestElkoProductsVersionNr);
        }
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("insertedElkoProducts", elkoProducts);
    }
}
