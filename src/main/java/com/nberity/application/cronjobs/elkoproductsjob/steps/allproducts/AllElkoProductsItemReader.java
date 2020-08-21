package com.nberity.application.cronjobs.elkoproductsjob.steps.allproducts;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllElkoProductsItemReader implements ItemReader<ElkoProduct> {

    private ElkoProductsJobService elkoProductsJobService;

    private List<ElkoProduct> elkoProducts;
    private int index;
    private Integer latestElkoProductsVersionNr = 0;

    @Autowired
    private AllElkoProductsItemReader(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @BeforeStep
    public void gatherAllElkoProducts(StepExecution stepExecution) {
        index = 0;
        System.out.println("INDEX: " + index);
        System.out.println("Starting to gather all ELKO products from WS...");
        elkoProducts = elkoProductsJobService.getAllElkoProductsFromWebService();
        System.out.println("Figuring out the latest product version nr...");
        latestElkoProductsVersionNr = elkoProductsJobService.getMostRecentProductsVersionNr() + 1;
        stepExecution.getJobExecution().getExecutionContext().putInt("latestVersionNr", latestElkoProductsVersionNr);
        System.out.println("Version nr for the new products shall be: " + latestElkoProductsVersionNr);
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
