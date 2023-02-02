package com.NightFury.UserAndCartService.User.Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart_address_entries")
public class CartAddressModel {

	@Id
	private String pk;
	private String guid;
	private String addressType;
	private String addressTitle;
	private String businessType;
	private Timestamp updatedAt;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressTitle() {
		return addressTitle;
	}

	public void setAddressTitle(String addressTitle) {
		this.addressTitle = addressTitle;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
