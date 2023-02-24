package com.NightFury.UserAndCartService.Order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Order.Entity.OrderEntryModel;

@Repository
public interface OrderEntryRepository extends JpaRepository<OrderEntryModel, String> {
	
	Optional<List<OrderEntryModel>> findByOrderId(String orderId);
}
