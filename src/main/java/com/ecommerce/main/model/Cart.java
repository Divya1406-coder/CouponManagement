package com.ecommerce.main.model;

import java.util.List;

public class Cart {
	
	private List<CartItem> items;
	private double totalPrice;
	private double totalDiscount;
	private double finalPrice;

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
		this.totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
}