package com.NightFury.UserAndCartService.Order.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_entry")
public class OrderEntryModel {

	@Id
	private String pk;
	private String orderId;
	private String entryId;
	private String entryCode;
	private String productModelId;
	private String codeShortDescription;
	private double basePrice;
	private double payablePrice;
	private int quantity;
	private double lineTotal;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(String productModelId) {
		this.productModelId = productModelId;
	}

	public String getCodeShortDescription() {
		return codeShortDescription;
	}

	public void setCodeShortDescription(String codeShortDescription) {
		this.codeShortDescription = codeShortDescription;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getPayablePrice() {
		return payablePrice;
	}

	public void setPayablePrice(double payablePrice) {
		this.payablePrice = payablePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}

}
