package com.NightFury.UserAndCartService.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;

@Repository
@Transactional
public interface CartProductEntryRepository extends JpaRepository<CartProductEntryModel, String> {
	
	@Query( value = "SELECT * FROM cart_product_entries WHERE guid = ?1 AND product_code = ?2" , nativeQuery = true)
	public List<CartProductEntryModel> reteriveProductEntry(String guid , String productCode);
	
	@Query( value = "SELECT product_code FROM cart_product_entries WHERE guid = ?1" , nativeQuery = true)
	public List<String> reteriveProductEntries(String guid);
	
	@Modifying
	@Query( value = "UPDATE cart_product_entries SET quantity = ?1 WHERE guid = ?2 AND product_code = ?3" , nativeQuery = true)
	public int updateProductQuantity(int Quantity , String guid, String productCode);
	
	@Modifying
	@Query(value = "DELETE FROM cart_product_entries WHERE guid = ?1 AND product_code = ?2", nativeQuery = true)
	public int removeProductEntry(String guid , String productCode);
	
	public List<CartProductEntryModel> findByGuid(String guid);
	
}
