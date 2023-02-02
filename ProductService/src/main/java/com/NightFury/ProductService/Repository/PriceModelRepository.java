package com.NightFury.ProductService.Repository;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.NightFury.ProductService.Entity.PriceModel;

@Repository
@Transactional
public interface PriceModelRepository extends JpaRepository<PriceModel, String> {
	
	@Query(value="SELECT p_id FROM product_price_table WHERE p_id IN :pnc AND p_price_value BETWEEN :lowPrice AND :highPrice",  
			nativeQuery = true)
	HashSet<String> filterProductListBasedOnPriceRange(@Param("pnc") List<String> products, @Param("lowPrice") double lowestPrice,
			@Param("highPrice") double highestPrice);
}
