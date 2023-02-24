package com.NightFury.UserAndCartService.Order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Order.Entity.OrderCostSummaryModel;

@Repository
public interface OrderCostSummaryRepository extends JpaRepository<OrderCostSummaryModel, String> {
	
	Optional<List<OrderCostSummaryModel>> findByOrderId(String orderId);
}
