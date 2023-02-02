package com.NightFury.WelcomeService.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "why_us_marketing_content")
public class WelcomePageWhyUsModel {

	private int wRank;
	private String wContent;

	@Id
	private String pk;

	public int getwRank() {
		return wRank;
	}

	public void setwRank(int wRank) {
		this.wRank = wRank;
	}

	public String getwContent() {
		return wContent;
	}

	public void setwContent(String wContent) {
		this.wContent = wContent;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

}
