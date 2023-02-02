package com.NightFury.ProductService.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.NightFury.ProductService.Entity.ProductModel;

@Repository
@Transactional
public interface ProductModelRepository extends JpaRepository<ProductModel, String> {
	
	@Query(value = "SELECT p_id FROM product_base_table WHERE p_category IN :ctgry" , nativeQuery = true)
	List<String> getProductsBasedOnCategory(@Param("ctgry") List<String> category);
}
