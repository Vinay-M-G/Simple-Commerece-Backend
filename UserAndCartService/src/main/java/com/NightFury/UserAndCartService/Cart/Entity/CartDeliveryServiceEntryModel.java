package com.NightFury.UserAndCartService.Cart.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart_delivery_service_entries")
public class CartDeliveryServiceEntryModel {

	private String pk;
	
	@Id
	private String guid;
	private String serviceCode;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
