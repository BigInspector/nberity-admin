package com.nberity.application.cronjobs.elkoproductsjob.steps.productattributes;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElkoProductAttributeItemReader implements ItemReader<ElkoProduct> {

    private int index;
    private List<ElkoProduct> elkoProducts;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        index = 0;
        System.out.println("Beginning to update product attributes");
        elkoProducts = (List<ElkoProduct>) stepExecution.getJobExecution().getExecutionContext().get("insertedElkoProducts");
    }

    @Override
    public ElkoProduct read() {
        if (index < elkoProducts.size()) {
            return elkoProducts.get(index++);
        } else {
            return null;
        }
    }
}
