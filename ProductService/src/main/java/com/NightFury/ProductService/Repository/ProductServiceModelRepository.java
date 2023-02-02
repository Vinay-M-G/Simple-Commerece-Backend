package com.NightFury.ProductService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductServiceModel;

@Repository
public interface ProductServiceModelRepository extends JpaRepository<ProductServiceModel, String> {

}
