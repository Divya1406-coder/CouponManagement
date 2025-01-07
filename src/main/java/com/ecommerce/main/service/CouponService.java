package com.ecommerce.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.model.*;
import com.ecommerce.main.repo.CouponRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;

	public Coupon createCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public Coupon getCoupon(Long id) {
		return couponRepository.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));
	}

	public Coupon updateCoupon(Long id, Coupon updatedCoupon) {
		Coupon existingCoupon = getCoupon(id);
		existingCoupon.setType(updatedCoupon.getType());
		existingCoupon.setDetails(updatedCoupon.getDetails());
		return couponRepository.save(existingCoupon);
	}

	public String deleteCoupon(Long id) {
		couponRepository.deleteById(id);
		return "Coupon deleted successfully.";
	}

	public List<ApplicableCoupon> getApplicableCoupons(Cart cart) {
		List<ApplicableCoupon> applicableCoupons = new ArrayList<>();
		for (Coupon coupon : couponRepository.findAll()) {
			double discount = coupon.calculateDiscount(cart);
			if (discount > 0) {
				applicableCoupons.add(new ApplicableCoupon(coupon.getId(), coupon.getType(), discount));
			}
		}
		return applicableCoupons;
	}

	public Cart applyCoupon(Long id, Cart cart) {
		Coupon coupon = getCoupon(id);
		double discount = coupon.calculateDiscount(cart);
		cart.setTotalDiscount(discount);
		cart.setFinalPrice(cart.getTotalPrice() - discount);
		return cart;
	}
}
