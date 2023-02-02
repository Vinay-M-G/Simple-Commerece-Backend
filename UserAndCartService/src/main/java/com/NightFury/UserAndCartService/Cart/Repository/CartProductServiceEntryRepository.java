package com.NightFury.UserAndCartService.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.NightFury.UserAndCartService.Cart.Entity.CartProductServiceEntryModel;

@Repository
@Transactional
public interface CartProductServiceEntryRepository extends JpaRepository<CartProductServiceEntryModel, String> {
	
	@Modifying
	@Query(value = "DELETE FROM cart_service_entries WHERE guid = ?1 AND service_code = ?2 AND product_code = ?3" , nativeQuery = true)
	public int removeServiceEntryFromCart(String guid, String serviceId, String productCode);
	
	@Query( value = "SELECT pk FROM cart_service_entries WHERE guid = ?1 AND service_code = ?2 AND product_code = ?3" , nativeQuery = true)
	public String checkServiceDuplicateEntry(String guid, String serviceId, String productCode);
	
	@Query(value = "SELECT service_code FROM cart_service_entries WHERE guid = ?1 AND product_code = ?2" , nativeQuery = true)
	public List<String> retriveServiceEntries(String guid, String productCode);

}
