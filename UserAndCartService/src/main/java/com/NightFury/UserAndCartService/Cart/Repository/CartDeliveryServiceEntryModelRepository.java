package com.NightFury.UserAndCartService.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NightFury.UserAndCartService.Cart.Entity.CartDeliveryServiceEntryModel;

@Repository
public interface CartDeliveryServiceEntryModelRepository extends JpaRepository<CartDeliveryServiceEntryModel, String> {

}
