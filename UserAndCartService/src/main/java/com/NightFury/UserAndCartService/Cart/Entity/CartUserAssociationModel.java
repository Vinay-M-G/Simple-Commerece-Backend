package com.NightFury.UserAndCartService.Cart.Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart_user_table")
public class CartUserAssociationModel {

	@Id
	private String guid;
	private String userId;
	private boolean isConvertedToOrder;
	private Timestamp createdAt;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isConvertedToOrder() {
		return isConvertedToOrder;
	}

	public void setConvertedToOrder(boolean isConvertedToOrder) {
		this.isConvertedToOrder = isConvertedToOrder;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
