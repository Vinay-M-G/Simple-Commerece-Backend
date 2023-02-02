package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_availability")
public class ProductAvailabilityModel {
	
	@Id
	private String pk;
	private String pId;
	private boolean isSellableOnline;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isSellableOnline() {
		return isSellableOnline;
	}

	public void setSellableOnline(boolean isSellableOnline) {
		this.isSellableOnline = isSellableOnline;
	}

}
