package com.ecommerce.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.model.ApplicableCoupon;
import com.ecommerce.main.model.Cart;
import com.ecommerce.main.model.CartWrapper;
import com.ecommerce.main.model.Coupon;
import com.ecommerce.main.service.CouponService;

@RestController
@RequestMapping("/coupons")
public class CouponController {

	@Autowired
	private CouponService couponService;

	@PostMapping
	public Coupon createCoupon(@RequestBody Coupon coupon) {
		return couponService.createCoupon(coupon);
	}

	@GetMapping
	public List<Coupon> getAllCoupons() {
		return couponService.getAllCoupons();
	}

	@GetMapping("/{id}")
	public Coupon getCoupon(@PathVariable Long id) {
		return couponService.getCoupon(id);
	}

	@PutMapping("/{id}")
	public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon updatedCoupon) {
		return couponService.updateCoupon(id, updatedCoupon);
	}

	@DeleteMapping("/{id}")
	public String deleteCoupon(@PathVariable Long id) {
		return couponService.deleteCoupon(id);
	}

	@PostMapping("/applicable-coupons")
	public List<ApplicableCoupon> getApplicableCoupons(@RequestBody CartWrapper cartWrapper) {
		return couponService.getApplicableCoupons(cartWrapper.getCart());
	}

	@PostMapping("/apply-coupon/{id}")
	public Cart applyCoupon(@PathVariable Long id, @RequestBody CartWrapper cartWrapper) {
		return couponService.applyCoupon(id, cartWrapper.getCart());
	}
}
