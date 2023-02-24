package com.NightFury.UserAndCartService.User.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;
import com.NightFury.UserAndCartService.User.Entity.UserAddressModel;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressModel, String> {

	String baseQueryForExistingAddress = "SELECT pk FROM address_entries WHERE email_id = ?1"
			+ " AND address_type = ?2 AND address_title = ?3 AND business_type = ?4";

	@Query(value = baseQueryForExistingAddress, nativeQuery = true)
	public Optional<String> getExistingAddressId(String emailId , String addressType, String addressTitle, String busnisessType);
	
	List<UserAddressModel> findByEmailIdAndBusinessType(String emailId, String busnisessType);
	
	Optional<List<UserAddressModel>> findByEmailIdAndBusinessTypeAndAddressTitleAndAddressType(String emailId, String busnisessType, String addressTitle, String addressType);
	

}
