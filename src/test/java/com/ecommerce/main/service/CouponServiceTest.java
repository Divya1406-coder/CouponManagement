package com.ecommerce.main.service;

import com.ecommerce.main.model.Cart;
import com.ecommerce.main.model.Coupon;
import com.ecommerce.main.model.CartItem;
import com.ecommerce.main.model.ApplicableCoupon;
import com.ecommerce.main.repo.CouponRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponServiceTest {

	@InjectMocks
	private CouponService couponService;

	@Mock
	private CouponRepository couponRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateCoupon() {
		Coupon coupon = new Coupon();
		coupon.setType("cart-wise");
		coupon.setDetails(Map.of("threshold", 100.0, "discount", 10.0));

		when(couponRepository.save(coupon)).thenReturn(coupon);

		Coupon createdCoupon = couponService.createCoupon(coupon);

		assertNotNull(createdCoupon);
		assertEquals("cart-wise", createdCoupon.getType());
		verify(couponRepository, times(1)).save(coupon);
	}

	@Test
	void testGetAllCoupons() {
		List<Coupon> coupons = List.of(new Coupon(), new Coupon());
		when(couponRepository.findAll()).thenReturn(coupons);

		List<Coupon> result = couponService.getAllCoupons();

		assertEquals(2, result.size());
		verify(couponRepository, times(1)).findAll();
	}

	@Test
	void testGetCoupon() {
		Coupon coupon = new Coupon();
		coupon.setId(1L);
		when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

		Coupon result = couponService.getCoupon(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		verify(couponRepository, times(1)).findById(1L);
	}

	@Test
	void testUpdateCoupon() {
		Coupon existingCoupon = new Coupon();
		existingCoupon.setId(1L);
		existingCoupon.setType("cart-wise");
		existingCoupon.setDetails(Map.of("threshold", 100.0, "discount", 10.0));

		Coupon updatedCoupon = new Coupon();
		updatedCoupon.setType("product-wise");
		updatedCoupon.setDetails(Map.of("product_id", 101, "discount", 20.0));

		when(couponRepository.findById(1L)).thenReturn(Optional.of(existingCoupon));
		when(couponRepository.save(existingCoupon)).thenReturn(existingCoupon);

		Coupon result = couponService.updateCoupon(1L, updatedCoupon);

		assertNotNull(result);
		assertEquals("product-wise", result.getType());
		assertEquals(20.0, result.getDetails().get("discount"));
		verify(couponRepository, times(1)).findById(1L);
		verify(couponRepository, times(1)).save(existingCoupon);
	}

	@Test
	void testDeleteCoupon() {
		doNothing().when(couponRepository).deleteById(1L);

		String result = couponService.deleteCoupon(1L);

		assertEquals("Coupon deleted successfully.", result);
		verify(couponRepository, times(1)).deleteById(1L);
	}

	@Test
	void testApplyCoupon() {
		Coupon coupon = new Coupon();
		coupon.setId(1L);
		coupon.setType("cart-wise");
		Map<String, Object> details = new HashMap<>();
		details.put("threshold", 50.0);
		details.put("discount", 10.0);
		coupon.setDetails(details);

		Cart cart = new Cart();
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(1, 2, 30.0));
		cart.setItems(cartItems);

		Mockito.when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

		Cart updatedCart = couponService.applyCoupon(1L, cart);

		Assertions.assertEquals(6.0, updatedCart.getTotalDiscount(), "The total discount should be 10% of 60.");
		Assertions.assertEquals(54.0, updatedCart.getFinalPrice(), "The final price should reflect the discount.");
	}

	@Test
	void testGetApplicableCoupons() {
		Coupon coupon = new Coupon();
		coupon.setId(1L);
		coupon.setType("cart-wise");
		Map<String, Object> details = new HashMap<>();
		details.put("threshold", 50.0);
		details.put("discount", 10.0);
		coupon.setDetails(details);

		Mockito.when(couponRepository.findAll()).thenReturn(List.of(coupon));

		Cart cart = new Cart();
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(1, 2, 30.0));
		cart.setItems(cartItems);

		List<ApplicableCoupon> applicableCoupons = couponService.getApplicableCoupons(cart);

		Assertions.assertEquals(1, applicableCoupons.size(), "One coupon should be applicable.");
		Assertions.assertEquals(6.0, applicableCoupons.get(0).getDiscount(),
				"The discount should be correctly calculated.");
	}

}
