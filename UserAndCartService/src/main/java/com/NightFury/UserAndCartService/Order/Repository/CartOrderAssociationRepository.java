package com.NightFury.UserAndCartService.Order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Order.Entity.CartOrderAssociationModel;

@Repository
public interface CartOrderAssociationRepository extends JpaRepository<CartOrderAssociationModel, String> {
	
	Optional<List<CartOrderAssociationModel>> findByOrderId(String orderId); 
}
