package com.nberity.application.controller;

import com.nberity.application.cronjobs.elkoproductsjob.service.ElkoProductsJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class ElkoProductController {

    private ElkoProductsJobService elkoProductsJobService;

    @Autowired
    public ElkoProductController(ElkoProductsJobService elkoProductsJobService) {
        this.elkoProductsJobService = elkoProductsJobService;
    }

    @RequestMapping(value = "/rest/elko-products", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAvailableElkoProductsInJson() {
        byte[] bytes = elkoProductsJobService.getAvailableElkoProductsInJson().getElkoProductJson().getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
