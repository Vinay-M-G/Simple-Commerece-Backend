package com.NightFury.ProductService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NightFury.ProductService.Entity.ProductAvailabilityModel;

public interface ProductAvailabilityModelRepository extends JpaRepository<ProductAvailabilityModel, String> {

	@Query(value = "SELECT p_id FROM product_availability WHERE p_id IN :pnc AND is_sellable_online = true", nativeQuery = true)
	List<String> filterAvailableProducts(@Param("pnc") List<String> products);
}
