package com.nberity.application.cronjobs.elkoproductsjob.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProductInfoJson;
import com.nberity.application.cronjobs.elkoproductsjob.repository.ElkoProductInfoJsonRepository;
import com.nberity.application.cronjobs.elkoproductsjob.repository.ElkoProductsJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ElkoProductsJobService {

    private ElkoProductJobWsClient elkoProductJobWsClient;

    private ElkoProductsJobRepository elkoProductsJobRepository;

    private ElkoProductInfoJsonRepository elkoProductInfoJsonRepository;

    @Autowired
    private ElkoProductsJobService(ElkoProductJobWsClient elkoProductJobWsClient, ElkoProductsJobRepository elkoProductsJobRepository,
                                   ElkoProductInfoJsonRepository elkoProductInfoJsonRepository) {
        this.elkoProductJobWsClient = elkoProductJobWsClient;
        this.elkoProductsJobRepository = elkoProductsJobRepository;
        this.elkoProductInfoJsonRepository = elkoProductInfoJsonRepository;
    }

    public List<ElkoProduct> getAllElkoProductsFromWebService() {
        long start = System.currentTimeMillis();
        String allElkoProductsJsonArrayString =  elkoProductJobWsClient.getAllElkoProductsJsonArrayString();
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed all products: " + ((end - start) / 1000));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(allElkoProductsJsonArrayString, ElkoProduct[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateElkoProductMeasurements(List<ElkoProduct> elkoProducts) {
        for (ElkoProduct elkoProduct : elkoProducts) {
            String productInfo = elkoProductJobWsClient.getProductInformation(elkoProduct.getElkoCode());
            elkoProduct.setProductDescriptionJson(productInfo);
        }
        elkoProductsJobRepository.saveAll(elkoProducts);
    }

    public Integer getMostRecentProductsVersionNr() {
        Integer topVersionNr = elkoProductsJobRepository.getTopVersionNumber();
        if (topVersionNr == null) {
            topVersionNr = 0;
        }
        return topVersionNr;
    }

    public void saveAllElkoProducts(List<ElkoProduct> items) {
        elkoProductsJobRepository.saveAll(items);
    }

    public List<ElkoProduct> getAllLatestElkoProductsWithAvailableStock() {
        return elkoProductsJobRepository.getAllLatestElkoProductsWithAvailableStock();
    }

    public void saveElkoProductsJson(ElkoProductInfoJson elkoProductInfoJson) {
        elkoProductInfoJsonRepository.save(elkoProductInfoJson);
    }

    public ElkoProductInfoJson getAvailableElkoProductsInJson() {
        return elkoProductInfoJsonRepository.getAvailableElkoProductsInJson();
    }
}
