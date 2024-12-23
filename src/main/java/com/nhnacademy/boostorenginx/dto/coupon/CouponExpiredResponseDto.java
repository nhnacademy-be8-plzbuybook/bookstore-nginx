package com.nhnacademy.boostorenginx.dto.coupon;

import com.nhnacademy.boostorenginx.entity.Coupon;

import java.time.LocalDateTime;

public record CouponExpiredResponseDto(
        Long couponId,
        String code,
        String status,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {
    public static CouponExpiredResponseDto fromEntity(Coupon coupon) {
        return new CouponExpiredResponseDto(
                coupon.getId(),
                coupon.getCode(),
                coupon.getStatus().toString(),
                coupon.getIssuedAt(),
                coupon.getExpiredAt()
        );
    }
}
