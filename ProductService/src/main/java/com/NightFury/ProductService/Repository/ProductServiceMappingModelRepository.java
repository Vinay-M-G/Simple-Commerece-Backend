package com.NightFury.ProductService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductServiceMappingModel;

@Repository
public interface ProductServiceMappingModelRepository extends JpaRepository<ProductServiceMappingModel, String> {
	
	public static final String baseQueryForDeliveryService = "SELECT pst.s_id FROM product_service_mapping psm, product_service_table pst"
			+ " WHERE psm.s_id = pst.s_id AND psm.p_id = ?1 AND pst.s_service_type = 'Delivery'";
	
	@Query(value = "SELECT s_id FROM product_service_mapping WHERE p_id = ?1" , nativeQuery = true)
	public List<String> retriveServiceIds(String productId);
	
	@Query(value = baseQueryForDeliveryService , nativeQuery = true)
	public List<String> retriveDeliveryServiceIds(String productId);
}
