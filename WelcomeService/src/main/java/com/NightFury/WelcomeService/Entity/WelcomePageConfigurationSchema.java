package com.NightFury.WelcomeService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "welcome_microservice_configurations")
public class WelcomePageConfigurationSchema {
	
	@Id
	private String pk;
	private String pKey;
	private String pValue;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getpKey() {
		return pKey;
	}

	public void setpKey(String pKey) {
		this.pKey = pKey;
	}

	public String getpValue() {
		return pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

}
