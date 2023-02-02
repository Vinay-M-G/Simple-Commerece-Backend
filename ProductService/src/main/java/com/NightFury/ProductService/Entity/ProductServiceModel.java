package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_service_table")
public class ProductServiceModel {

	@Id
	private String pk;
	private String sId;
	private String sServiceName;
	private String sServiceTerms;
	private String sShortDescription;
	private String sServiceType;
	private boolean isMultiplier;

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

	public String getsServiceName() {
		return sServiceName;
	}

	public void setsServiceName(String sServiceName) {
		this.sServiceName = sServiceName;
	}

	public String getsServiceTerms() {
		return sServiceTerms;
	}

	public void setsServiceTerms(String sServiceTerms) {
		this.sServiceTerms = sServiceTerms;
	}

	public String getsShortDescription() {
		return sShortDescription;
	}

	public void setsShortDescription(String sShortDescription) {
		this.sShortDescription = sShortDescription;
	}

	public String getsServiceType() {
		return sServiceType;
	}

	public void setsServiceType(String sServiceType) {
		this.sServiceType = sServiceType;
	}

	public Boolean getIsMultiplier() {
		return isMultiplier;
	}

	public void setIsMultiplier(Boolean isMultiplier) {
		this.isMultiplier = isMultiplier;
	}

}
