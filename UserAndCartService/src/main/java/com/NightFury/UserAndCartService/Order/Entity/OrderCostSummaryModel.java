package com.NightFury.UserAndCartService.Order.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_cost_summary")
public class OrderCostSummaryModel {

	@Id
	private String orderId;
	private double lineTotalWithDiscount;
	private double serviceTotalWithDiscount;
	private double orderTotalWithDiscount;
	private double lineTotalWithoutDiscount;
	private double serviceTotalWithoutDiscount;
	private double orderTotalWithoutDiscount;
	private double totalQuantity;
	private double orderSavings;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getLineTotalWithDiscount() {
		return lineTotalWithDiscount;
	}

	public void setLineTotalWithDiscount(double lineTotalWithDiscount) {
		this.lineTotalWithDiscount = lineTotalWithDiscount;
	}

	public double getServiceTotalWithDiscount() {
		return serviceTotalWithDiscount;
	}

	public void setServiceTotalWithDiscount(double serviceTotalWithDiscount) {
		this.serviceTotalWithDiscount = serviceTotalWithDiscount;
	}

	public double getOrderTotalWithDiscount() {
		return orderTotalWithDiscount;
	}

	public void setOrderTotalWithDiscount(double orderTotalWithDiscount) {
		this.orderTotalWithDiscount = orderTotalWithDiscount;
	}

	public double getLineTotalWithoutDiscount() {
		return lineTotalWithoutDiscount;
	}

	public void setLineTotalWithoutDiscount(double lineTotalWithoutDiscount) {
		this.lineTotalWithoutDiscount = lineTotalWithoutDiscount;
	}

	public double getServiceTotalWithoutDiscount() {
		return serviceTotalWithoutDiscount;
	}

	public void setServiceTotalWithoutDiscount(double serviceTotalWithoutDiscount) {
		this.serviceTotalWithoutDiscount = serviceTotalWithoutDiscount;
	}

	public double getOrderTotalWithoutDiscount() {
		return orderTotalWithoutDiscount;
	}

	public void setOrderTotalWithoutDiscount(double orderTotalWithoutDiscount) {
		this.orderTotalWithoutDiscount = orderTotalWithoutDiscount;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getOrderSavings() {
		return orderSavings;
	}

	public void setOrderSavings(double orderSavings) {
		this.orderSavings = orderSavings;
	}



}
