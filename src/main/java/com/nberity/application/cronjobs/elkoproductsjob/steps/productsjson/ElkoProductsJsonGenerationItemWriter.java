package com.nberity.application.cronjobs.elkoproductsjob.steps.productsjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProductGeneralDTO;
import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProductInfoJson;
import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElkoProductsJsonGenerationItemWriter implements ItemWriter<ElkoProduct> {

    private ElkoProductsJobService elkoProductsJobService;

    private JSONArray allProductsArray;

    private int index;

    @Autowired
    private ElkoProductsJsonGenerationItemWriter(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        index = 0;
        allProductsArray = new JSONArray();
    }

    @Override
    public void write(List<? extends ElkoProduct> items) {
        System.out.println("Generating JSON for batch: " + index);
        for (ElkoProduct elkoProduct : items) {
            JSONArray oneProductArray = new JSONArray();

            JSONObject elkoCodeJsonObject = new JSONObject();
            elkoCodeJsonObject.put("elkoCode", elkoProduct.getElkoCode());
            oneProductArray.put(elkoCodeJsonObject);

            JSONObject generalElkoProductData = new JSONObject(generateJsonForGeneralElkoProductData(elkoProduct));
            oneProductArray.put(generalElkoProductData);

            JSONArray productDescriptionArray = new JSONArray(elkoProduct.getProductDescriptionJson());
            List<String> objectsInStringForm = new ArrayList<>();
            for (int i = 0; i < productDescriptionArray.length(); i++) {
                objectsInStringForm.add(productDescriptionArray.get(i).toString());
            }
            parseProductShippingMeasurements(objectsInStringForm, oneProductArray);
            for (int j = 0; j < objectsInStringForm.size(); j++) {
                JSONObject object = new JSONObject(objectsInStringForm.get(j));
                if (!(object.get("criteria").equals("Full Description Line") || object.get("criteria").equals("Shipping Box Depth") ||
                        object.get("criteria").equals("Shipping Box Weight") || object.get("criteria").equals("Shipping Box Height")
                        || object.get("criteria").equals("Shipping box quantity") || object.get("criteria").equals("Unit Brutto Volume")
                        || object.get("criteria").equals("Shipping Box Width"))) {
                    oneProductArray.put(object);
                }
            }

            allProductsArray.put(oneProductArray);
        }
        index++;
    }

    private void parseProductShippingMeasurements(List<String> objectsInStringForm, JSONArray oneProductArray) {
        for (int j = 0; j < objectsInStringForm.size(); j++) {
            JSONObject object = new JSONObject(objectsInStringForm.get(j));
            if (object.get("criteria").equals("Shipping Box Depth")) {
                oneProductArray.put(object);
            }
            if (object.get("criteria").equals("Shipping Box Height")) {
                oneProductArray.put(object);
            }
            if (object.get("criteria").equals("Shipping Box Weight")) {
                oneProductArray.put(object);
            }
            if (object.get("criteria").equals("Unit Brutto Volume")) {
                oneProductArray.put(object);
            }
            if (object.get("criteria").equals("Shipping Box Width")) {
                oneProductArray.put(object);
            }
        }
    }

    private String generateJsonForGeneralElkoProductData(ElkoProduct elkoProduct) {
        ElkoProductGeneralDTO elkoProductGeneralDTO = setUpElkoProductGeneralDTO(elkoProduct);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(elkoProductGeneralDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ElkoProductGeneralDTO setUpElkoProductGeneralDTO(ElkoProduct elkoProduct) {
        ElkoProductGeneralDTO elkoProductGeneralDTO = new ElkoProductGeneralDTO();
        elkoProductGeneralDTO.setId(elkoProduct.getId());
        elkoProductGeneralDTO.setElkoCode(elkoProduct.getElkoCode());
        elkoProductGeneralDTO.setName(shortenProductName(elkoProduct.getName()));
        elkoProductGeneralDTO.setManufacturerCode(elkoProduct.getManufacturerCode());
        elkoProductGeneralDTO.setVendorName(elkoProduct.getVendorName());
        elkoProductGeneralDTO.setVendorCode(elkoProduct.getVendorCode());
        elkoProductGeneralDTO.setCatalog(elkoProduct.getCatalog());
        elkoProductGeneralDTO.setQuantity(elkoProduct.getQuantity());
        elkoProductGeneralDTO.setPrice(elkoProduct.getPrice());
        elkoProductGeneralDTO.setDiscountPrice(elkoProduct.getDiscountPrice());
        elkoProductGeneralDTO.setImagePath(elkoProduct.getImagePath());
        elkoProductGeneralDTO.setThumbnailImagePath(elkoProduct.getThumbnailImagePath());
        elkoProductGeneralDTO.setFullDsc(elkoProduct.getFullDsc());
        elkoProductGeneralDTO.setCurrency(elkoProduct.getCurrency());
        elkoProductGeneralDTO.setHttpDescription(elkoProduct.getHttpDescription());
        elkoProductGeneralDTO.setPackagingQuantity(elkoProduct.getPackagingQuantity());
        elkoProductGeneralDTO.setWarranty(elkoProduct.getWarranty());
        elkoProductGeneralDTO.setEanCode(elkoProduct.getEanCode());
        elkoProductGeneralDTO.setObligatoryKit(elkoProduct.getObligatoryKit());
        elkoProductGeneralDTO.setReservedQuantity(elkoProduct.getReservedQuantity());
        elkoProductGeneralDTO.setPromDate(elkoProduct.getPromDate());
        elkoProductGeneralDTO.setPromQuant(elkoProduct.getPromQuant());
        elkoProductGeneralDTO.setQuantityForPrice2(elkoProduct.getQuantityForPrice2());
        elkoProductGeneralDTO.setPrice2(elkoProduct.getPrice2());
        elkoProductGeneralDTO.setLotNumber(elkoProduct.getLotNumber());
        elkoProductGeneralDTO.setCopyrightTax(elkoProduct.getCopyrightTax());
        elkoProductGeneralDTO.setIncomingQuantity(elkoProduct.getIncomingQuantity());
        return elkoProductGeneralDTO;
    }

    private String shortenProductName(String unProcessedName) {
        String[] splittedName = unProcessedName.split("\\|");
        try {
            return splittedName[1] + " " + splittedName[2] + " " + splittedName[3] + " " + splittedName[splittedName.length - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return unProcessedName;
        }
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        System.out.println("Saving final JSON to database...");
        ElkoProductInfoJson elkoProductInfoJson = new ElkoProductInfoJson();
        elkoProductInfoJson.setElkoProductJson(allProductsArray.toString());
        elkoProductsJobService.saveElkoProductsJson(elkoProductInfoJson);
        System.out.println("Elko product job finished");
    }
}
