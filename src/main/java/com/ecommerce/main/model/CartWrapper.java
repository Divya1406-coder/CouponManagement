package com.ecommerce.main.model;

public class CartWrapper {
	private Cart cart;

	public CartWrapper() {
	}

	public CartWrapper(Cart cart) {
		this.cart = cart;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
}
