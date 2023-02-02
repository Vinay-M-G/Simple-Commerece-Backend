package com.NightFury.ProductService.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.NightFury.ProductService.Entity.ProductCoreModel;

@Repository
@Transactional
public interface ProductCoreModelRepository extends JpaRepository<ProductCoreModel, String> {
	
	public String baseProductQueryForHighestPrice = "SELECT pp.pk, prod.p_id, prod.p_model_Id, prod.p_short_description, "
			+ "prod.p_long_description, prod.p_category, prod.p_image_url, "
			+ "pa.is_sellable_online, prod.p_premium_product, pp.p_price_type, " 
			+ "pp.p_price_value, pst.p_quantity, pst.p_stock_status "
			+ "FROM product_stock_table pst, product_price_table pp, product_base_table prod, " 
			+ "product_availability pa WHERE prod.p_id IN :pnc "
			+ "AND prod.p_id = pst.p_id AND prod.p_id = pp.p_id AND prod.p_id = pa.p_id " 
			+ "ORDER BY p_price_value DESC";
	
	public String baseProductQueryForLowestPrice = "SELECT pp.pk, prod.p_id, prod.p_model_Id, prod.p_short_description, "
			+ "prod.p_long_description, prod.p_category, prod.p_image_url, "
			+ "pa.is_sellable_online, prod.p_premium_product, pp.p_price_type, " 
			+ "pp.p_price_value, pst.p_quantity, pst.p_stock_status "
			+ "FROM product_stock_table pst, product_price_table pp, product_base_table prod, " 
			+ "product_availability pa WHERE prod.p_id IN :pnc "
			+ "AND prod.p_id = pst.p_id AND prod.p_id = pp.p_id AND prod.p_id = pa.p_id " 
			+ "ORDER BY p_price_value ASC";
	
	public String baseProductQueryForStock = "SELECT pp.pk, prod.p_id, prod.p_model_Id, prod.p_short_description, "
			+ "prod.p_long_description, prod.p_category, prod.p_image_url, "
			+ "pa.is_sellable_online, prod.p_premium_product, pp.p_price_type, " 
			+ "pp.p_price_value, pst.p_quantity, pst.p_stock_status "
			+ "FROM product_stock_table pst, product_price_table pp, product_base_table prod, " 
			+ "product_availability pa WHERE prod.p_id IN :pnc "
			+ "AND prod.p_id = pst.p_id AND prod.p_id = pp.p_id AND prod.p_id = pa.p_id " 
			+ "ORDER BY p_stock_status ASC";
	
	public String baseProductQueryForSingleProduct = "SELECT pp.pk, prod.p_id, prod.p_model_Id, prod.p_short_description, "
			+ "prod.p_long_description, prod.p_category, prod.p_image_url, "
			+ "pa.is_sellable_online, prod.p_premium_product, pp.p_price_type, " 
			+ "pp.p_price_value, pst.p_quantity, pst.p_stock_status "
			+ "FROM product_stock_table pst, product_price_table pp, product_base_table prod, " 
			+ "product_availability pa WHERE prod.p_id = ?1 "
			+ "AND prod.p_id = pst.p_id AND prod.p_id = pp.p_id AND prod.p_id = pa.p_id ";
	
	@Query(value = baseProductQueryForStock , nativeQuery = true)
	List<ProductCoreModel> getProductDetailsSortedByStock(@Param("pnc") List<String> products);
	
	@Query(value = baseProductQueryForHighestPrice , nativeQuery = true)
	List<ProductCoreModel> getProductDetailsSortedByHighestPrice(@Param("pnc") List<String> products);
	
	@Query(value = baseProductQueryForLowestPrice , nativeQuery = true)
	List<ProductCoreModel> getProductDetailsSortedByLowestPrice(@Param("pnc") List<String> products);
	
	@Query(value = baseProductQueryForSingleProduct , nativeQuery = true)
	List<ProductCoreModel> reteriveSingleProductDetail(String productCode);
}
