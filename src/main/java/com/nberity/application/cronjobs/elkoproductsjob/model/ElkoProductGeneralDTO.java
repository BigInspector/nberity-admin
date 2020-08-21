package com.nberity.application.cronjobs.elkoproductsjob.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ElkoProductGeneralDTO {

    private Long id;
    private Long elkoCode;
    private String name;
    private String manufacturerCode;
    private String vendorName;
    private String vendorCode;
    private String catalog;
    private String quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String imagePath;
    private String thumbnailImagePath;
    private String fullDsc;
    private String currency;
    private String httpDescription;
    private Integer packagingQuantity;
    private String warranty;
    private String eanCode;
    private Integer obligatoryKit;
    private Integer reservedQuantity;
    private Integer promDate;
    private Integer promQuant;
    private String quantityForPrice2;
    private BigDecimal price2;
    private String lotNumber;
    private BigDecimal copyrightTax;
    private Integer incomingQuantity;
}
