package com.NightFury.ProductService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_configuration")
public class ProductConfigurationModel {
	
	@Id
	private String pk;
	private String param;
	private String pcKey;
	private boolean pcValue;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getPcKey() {
		return pcKey;
	}

	public void setPcKey(String pcKey) {
		this.pcKey = pcKey;
	}

	public boolean isPcValue() {
		return pcValue;
	}

	public void setPcValue(boolean pcValue) {
		this.pcValue = pcValue;
	}

}
