package com.ecommerce.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.main.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}