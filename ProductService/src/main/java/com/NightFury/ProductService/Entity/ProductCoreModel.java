package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductCoreModel {

	@Id
	private String pk;
	private String pId;
	private String pModelId;
	private String pShortDescription;
	private String pLongDescription;
	private String pCategory;
	private String pImageUrl;
	private double pPriceValue;
	private String pPriceType;
	private String pStockStatus;
	private int pQuantity;
	private boolean isSellableOnline;
	private boolean pPremiumProduct;

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpModelId() {
		return pModelId;
	}

	public void setpModelId(String pModelId) {
		this.pModelId = pModelId;
	}

	public String getpShortDescription() {
		return pShortDescription;
	}

	public void setpShortDescription(String pShortDescription) {
		this.pShortDescription = pShortDescription;
	}

	public String getpLongDescription() {
		return pLongDescription;
	}

	public void setpLongDescription(String pLongDescription) {
		this.pLongDescription = pLongDescription;
	}

	public String getpCategory() {
		return pCategory;
	}

	public void setpCategory(String pCategory) {
		this.pCategory = pCategory;
	}

	public String getpImageUrl() {
		return pImageUrl;
	}

	public void setpImageUrl(String pImageUrl) {
		this.pImageUrl = pImageUrl;
	}

	public double getpPriceValue() {
		return pPriceValue;
	}

	public void setpPriceValue(double pPriceValue) {
		this.pPriceValue = pPriceValue;
	}

	public String getpPriceType() {
		return pPriceType;
	}

	public void setpPriceType(String pPriceType) {
		this.pPriceType = pPriceType;
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

	public boolean isSellableOnline() {
		return isSellableOnline;
	}

	public void setSellableOnline(boolean isSellableOnline) {
		this.isSellableOnline = isSellableOnline;
	}

	public boolean ispPremiumProduct() {
		return pPremiumProduct;
	}

	public void setpPremiumProduct(boolean pPremiumProduct) {
		this.pPremiumProduct = pPremiumProduct;
	}

}
