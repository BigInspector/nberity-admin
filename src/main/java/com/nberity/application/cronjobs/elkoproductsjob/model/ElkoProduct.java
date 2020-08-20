package com.nberity.application.cronjobs.elkoproductsjob.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "elko_product")
public class ElkoProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "elpr_id")
    private Long elprId;

    @Column(name = "id")
    private Long id;

    @Column(name = "elko_code")
    private Long elkoCode;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer_code")
    private String manufacturerCode;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "catalog")
    private String catalog;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "thumbnail_image_path")
    private String thumbnailImagePath;

    @Column(name = "full_description")
    private String fullDsc;

    @Column(name = "currency")
    private String currency;

    @Column(name = "http_description")
    private String httpDescription;

    @Column(name = "packaging_quantity")
    private Integer packagingQuantity;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "ean_code")
    private String eanCode;

    @Column(name = "obligatory_kit")
    private Integer obligatoryKit;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;

    @Column(name = "prom_date")
    private Integer promDate;

    @Column(name = "prom_quant")
    private Integer promQuant;

    @Column(name = "quantity_for_price_2")
    private String quantityForPrice2;

    @Column(name = "price_2")
    private BigDecimal price2;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "copyright_tax")
    private BigDecimal copyrightTax;

    @Column(name = "incoming_quantity")
    private Integer incomingQuantity;

    @Column(name = "version_nr")
    private Integer versionNr;

    @Column(name = "product_description_json")
    private String productDescriptionJson;

}
