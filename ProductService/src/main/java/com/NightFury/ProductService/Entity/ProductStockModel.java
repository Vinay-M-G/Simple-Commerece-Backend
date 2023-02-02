package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_stock_table")
public class ProductStockModel {

	@Id
	private String pk;
	private String pId;
	private String pStockStatus;
	private int pQuantity;

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

	public String getpStockStatus() {
		return pStockStatus;
	}

	public void setpStockStatus(String pStockStatus) {
		this.pStockStatus = pStockStatus;
	}

	public int getpQuantity() {
		return pQuantity;
	}

	public void setpQuantity(int pQuantity) {
		this.pQuantity = pQuantity;
	}

}
