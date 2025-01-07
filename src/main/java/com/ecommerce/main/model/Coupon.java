package com.ecommerce.main.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecommerce.main.utility.JsonToMapConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;

	@Convert(converter = JsonToMapConverter.class)
	@Column(columnDefinition = "TEXT")
	private Map<String, Object> details = new HashMap<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public double calculateDiscount(Cart cart) {
		switch (type) {
		case "cart-wise":
			double threshold = (double) details.get("threshold");
			double discountPercentage = (double) details.get("discount");
			return cart.getTotalPrice() > threshold ? cart.getTotalPrice() * discountPercentage / 100 : 0;

		case "product-wise":
			int productId = (int) details.get("product_id");
			double productDiscount = (double) details.get("discount");
			return cart.getItems().stream().filter(item -> item.getProductId() == productId)
					.mapToDouble(item -> item.getPrice() * item.getQuantity() * productDiscount / 100).sum();

		case "bxgy":
			List<Map<String, Integer>> buyProducts = (List<Map<String, Integer>>) details.get("buy_products");
			List<Map<String, Integer>> getProducts = (List<Map<String, Integer>>) details.get("get_products");
			int repetitionLimit = (int) details.get("repetition_limit");

			int maxRepetitions = repetitionLimit;
			for (Map<String, Integer> buy : buyProducts) {
				int requiredQty = buy.get("quantity");
				int availableQty = cart.getItems().stream().filter(item -> item.getProductId() == buy.get("product_id"))
						.mapToInt(CartItem::getQuantity).sum();
				maxRepetitions = Math.min(maxRepetitions, availableQty / requiredQty);
			}

			double totalDiscount = 0;
			for (Map<String, Integer> get : getProducts) {
				int freeQty = get.get("quantity") * maxRepetitions;
				double price = cart.getItems().stream().filter(item -> item.getProductId() == get.get("product_id"))
						.mapToDouble(CartItem::getPrice).findFirst().orElse(0);
				totalDiscount += freeQty * price;
			}

			return totalDiscount;

		default:
			return 0;
		}
	}
}