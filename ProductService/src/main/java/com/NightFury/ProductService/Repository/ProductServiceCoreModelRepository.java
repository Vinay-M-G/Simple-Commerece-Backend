package com.NightFury.ProductService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductServiceCoreModel;

@Repository
public interface ProductServiceCoreModelRepository extends JpaRepository<ProductServiceCoreModel, String> {
	
	public static final String baseQueryForSerivceDetails = "SELECT spt.pk, pst.s_id, pst.s_service_name, pst.s_service_terms, pst.s_short_description,"
			+ " pst.s_service_type, pst.is_multiplier, spt.s_price_value, spt.s_price_type FROM product_service_table pst ,"
			+ " service_price_table spt WHERE pst.s_id IN :productIds"
			+ " AND pst.s_id = spt.s_id AND pst.s_service_type = 'Product'";
	
	public static final String baseQueryForDeliverySerivceDetails = "SELECT spt.pk, pst.s_id, pst.s_service_name, pst.s_service_terms, pst.s_short_description,"
			+ " pst.s_service_type, pst.is_multiplier, spt.s_price_value, spt.s_price_type FROM product_service_table pst ,"
			+ " service_price_table spt WHERE pst.s_id IN :serviceIds"
			+ " AND pst.s_id = spt.s_id";
	
	@Query(value = baseQueryForSerivceDetails , nativeQuery = true)
	public List<ProductServiceCoreModel> retriveServiceDetails(List<String> productIds);
	
	@Query(value = baseQueryForDeliverySerivceDetails , nativeQuery = true)
	public List<ProductServiceCoreModel> retriveDeliveryServiceDetails(List<String> serviceIds);
	
}
