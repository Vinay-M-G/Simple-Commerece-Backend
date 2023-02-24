package com.NightFury.UserAndCartService.Cart.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Cart.Entity.CartUserAssociationModel;

@Repository
public interface CartUserAssociationRepository extends JpaRepository<CartUserAssociationModel, String> {
	
	@Query(value = "SELECT guid FROM cart_user_table WHERE user_id = ?1 AND is_converted_to_order = 0" , nativeQuery = true)
	public String reteriveCartId(String emailId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE cart_user_table SET is_converted_to_order = 1 WHERE guid = ?1" ,nativeQuery = true)
	public int updateOrderSuccessStatus(String guid);
}
