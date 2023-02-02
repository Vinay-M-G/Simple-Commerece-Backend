package com.NightFury.ProductService.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_base_table")
public class ProductModel {

	private String pK;

	@Id
	private String pId;
	private String pModelId;
	private String pShortDescription;
	private String pLongDescription;
	private String pCategory;
	private String pImageUrl;
	
	@Column(name = "p_premium_product")
	private boolean pPremiumProduct;

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

	public boolean ispPremiumProduct() {
		return pPremiumProduct;
	}

	public void setpPremiumProduct(boolean pPremiumProduct) {
		this.pPremiumProduct = pPremiumProduct;
	}

}
