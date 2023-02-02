package com.NightFury.ProductService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductServicePricingModel;

@Repository
public interface ProductServicePricingModelRepository extends JpaRepository<ProductServicePricingModel, String> {

}
