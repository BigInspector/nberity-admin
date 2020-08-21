package com.nberity.application.cronjobs.elkoproductsjob.steps.productattributes;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElkoProductAttributeItemWriter implements ItemWriter<ElkoProduct> {

    private ElkoProductsJobService elkoProductsJobService;

    int index;

    @Autowired
    private ElkoProductAttributeItemWriter(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        index = 0;
    }

    @Override
    public void write(List<? extends ElkoProduct> items) {
        System.out.println("Batch nr: " + index);
        long start = System.currentTimeMillis();
        elkoProductsJobService.updateElkoProductMeasurements((List<ElkoProduct>) items);

        long end = System.currentTimeMillis();
        System.out.println("Time elapsed for 1000 batch: " + ((end - start) / 1000));
        index++;
    }
}
