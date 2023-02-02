package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * This Entity Class will be used as base for sorting and filtering operations in v1
 */

@Entity
@Table(name = "product_price_table")
public class PriceModel {

	@Id
	private String pK;
	private String pId;
	private String pPriceValue;
	private String pPriceType;

	public String getpK() {
		return pK;
	}

	public void setpK(String pK) {
		this.pK = pK;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpPriceValue() {
		return pPriceValue;
	}

	public void setpPriceValue(String pPriceValue) {
		this.pPriceValue = pPriceValue;
	}

	public String getpPriceType() {
		return pPriceType;
	}

	public void setpPriceType(String pPriceType) {
		this.pPriceType = pPriceType;
	}

}
