package com.nberity.application.cronjobs.elkoproductsjob.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "elko_product_info_json")
public class ElkoProductInfoJson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "epij_id")
    private Long epijId;

    @Column(name = "elko_product_json")
    private String elkoProductJson;
}
