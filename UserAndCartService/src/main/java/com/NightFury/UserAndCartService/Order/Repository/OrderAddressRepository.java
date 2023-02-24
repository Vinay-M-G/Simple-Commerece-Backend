package com.NightFury.UserAndCartService.Order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddressModel, String> {
	
	Optional<List<OrderAddressModel>> findByOrderId(String orderId);
}
