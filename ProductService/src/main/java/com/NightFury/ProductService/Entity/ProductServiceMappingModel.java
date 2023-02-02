package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_service_mapping")
public class ProductServiceMappingModel {

	@Id
	private String pk;
	private String sId;
	private String pId;

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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

}
