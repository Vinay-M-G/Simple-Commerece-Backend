package com.NightFury.UserAndCartService.User.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.User.Entity.CartAddressModel;

@Repository
public interface CartAddressRepository extends JpaRepository<CartAddressModel, String> {
	
	@Query(value = "SELECT pk FROM cart_address_entries WHERE guid = ?1 AND address_type = ?2", nativeQuery = true)
	public Optional<String> getSelectedAddressValue(String guid, String addressType);
	
	@Query(value = "SELECT * FROM cart_address_entries WHERE guid = ?1", nativeQuery = true)
	public Optional<List<CartAddressModel>> getAddressValueByGuid(String guid);
	
}
