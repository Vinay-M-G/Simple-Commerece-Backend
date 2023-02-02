package com.NightFury.UserAndCartService.Cart.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CartCostModel {

	@Id
	private String pk;
	private double lineSubTotalWithoutDiscount = 0.0;
	private double lineSubTotalWithDiscount = 0.0;
	private double serviceSubTotalWithoutDiscount = 0.0;
	private double serviceSubTotalWithDiscount = 0.0;
	private double orderTotalWithoutDiscount = 0.0;
	private double orderTotalWithDiscount = 0.0;
	private double cartSavings = 0.0;
	private int cartProductCount = 0;

	public double getLineSubTotalWithoutDiscount() {
		return lineSubTotalWithoutDiscount;
	}

	public void setLineSubTotalWithoutDiscount(double lineSubTotalWithoutDiscount) {
		this.lineSubTotalWithoutDiscount = lineSubTotalWithoutDiscount;
	}

	public double getLineSubTotalWithDiscount() {
		return lineSubTotalWithDiscount;
	}

	public void setLineSubTotalWithDiscount(double lineSubTotalWithDiscount) {
		this.lineSubTotalWithDiscount = lineSubTotalWithDiscount;
	}

	public double getServiceSubTotalWithoutDiscount() {
		return serviceSubTotalWithoutDiscount;
	}

	public void setServiceSubTotalWithoutDiscount(double serviceSubTotalWithoutDiscount) {
		this.serviceSubTotalWithoutDiscount = serviceSubTotalWithoutDiscount;
	}

	public double getServiceSubTotalWithDiscount() {
		return serviceSubTotalWithDiscount;
	}

	public void setServiceSubTotalWithDiscount(double serviceSubTotalWithDiscount) {
		this.serviceSubTotalWithDiscount = serviceSubTotalWithDiscount;
	}

	public double getOrderTotalWithoutDiscount() {
		return orderTotalWithoutDiscount;
	}

	public void setOrderTotalWithoutDiscount(double orderTotalWithoutDiscount) {
		this.orderTotalWithoutDiscount = orderTotalWithoutDiscount;
	}

	public double getOrderTotalWithDiscount() {
		return orderTotalWithDiscount;
	}

	public void setOrderTotalWithDiscount(double orderTotalWithDiscount) {
		this.orderTotalWithDiscount = orderTotalWithDiscount;
	}

	public double getCartSavings() {
		return cartSavings;
	}

	public void setCartSavings(double cartSavings) {
		this.cartSavings = cartSavings;
	}

	public int getCartProductCount() {
		return cartProductCount;
	}

	public void setCartProductCount(int cartProductCount) {
		this.cartProductCount = cartProductCount;
	}

}
