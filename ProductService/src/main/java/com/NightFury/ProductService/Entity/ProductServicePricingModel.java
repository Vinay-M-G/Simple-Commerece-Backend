package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_price_table")
public class ProductServicePricingModel {

	@Id
	private String pk;
	private String sId;
	private double sPriceValue;
	private String sPriceType;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public double getsPriceValue() {
		return sPriceValue;
	}

	public void setsPriceValue(double sPriceValue) {
		this.sPriceValue = sPriceValue;
	}

	public String getsPriceType() {
		return sPriceType;
	}

	public void setsPriceType(String sPriceType) {
		this.sPriceType = sPriceType;
	}

}
