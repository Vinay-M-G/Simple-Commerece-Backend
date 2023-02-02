package com.NightFury.ProductService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductConfigurationModel;

@Repository
public interface ProductConfigurationRepository extends JpaRepository<ProductConfigurationModel, String> {

}
