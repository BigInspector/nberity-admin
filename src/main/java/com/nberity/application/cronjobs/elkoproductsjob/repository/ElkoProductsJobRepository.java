package com.nberity.application.cronjobs.elkoproductsjob.repository;

import com.nberity.application.cronjobs.elkoproductsjob.model.ElkoProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElkoProductsJobRepository extends JpaRepository<ElkoProduct, Long> {

    @Query(value = "SELECT elpr.version_nr FROM elko_product elpr ORDER BY elpr.version_nr DESC LIMIT 1", nativeQuery = true)
    Integer getTopVersionNumber();

    @Query(value = "DELETE FROM elko_product elpr WHERE elpr.version_nr < ( SELECT MAX(version_nr) FROM " +
            "elko_product ) - 1", nativeQuery = true)
    void deleteOldVersionNumbers();

    @Query(value = "SELECT * FROM elko_product elpr WHERE elpr.version_nr = (SELECT MAX(version_nr) FROM " +
            "elko_product)", nativeQuery = true)
    List<ElkoProduct> getAllLatestElkoProducts();
}
