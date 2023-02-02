package com.NightFury.ProductService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NightFury.ProductService.Entity.ProductStockModel;

public interface ProductStockModelRepository extends JpaRepository<ProductStockModel, String> {
	
	@Query(value="SELECT p_id FROM product_stock_table WHERE  p_id IN :pnc AND p_stock_status IN ('INS','LS')" , 
			nativeQuery=true)
	List<String> filterBasedOnStock(@Param("pnc") List<String> products);
	
}
