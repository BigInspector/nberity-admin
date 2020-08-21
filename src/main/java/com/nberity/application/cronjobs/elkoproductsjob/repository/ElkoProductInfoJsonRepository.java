package com.nberity.application.cronjobs.elkoproductsjob.repository;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProductInfoJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ElkoProductInfoJsonRepository extends JpaRepository<ElkoProductInfoJson, Long> {

    @Query(value = "SELECT * FROM elko_product_info_json ORDER BY epij_id DESC LIMIT 1", nativeQuery = true)
    ElkoProductInfoJson getAvailableElkoProductsInJson();
}
