package com.NightFury.UserAndCartService.Cart.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart_service_entries")
public class CartProductServiceEntryModel {

	@Id
	private String pk;
	private String guid;
	private String productCode;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
