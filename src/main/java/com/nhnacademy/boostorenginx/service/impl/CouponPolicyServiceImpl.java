package com.nhnacademy.boostorenginx.service.impl;

import com.nhnacademy.boostorenginx.dto.couponpolicy.CouponPolicyIdRequestDto;
import com.nhnacademy.boostorenginx.dto.couponpolicy.CouponPolicyNameRequestDto;
import com.nhnacademy.boostorenginx.dto.couponpolicy.CouponPolicySaveRequestDto;
import com.nhnacademy.boostorenginx.dto.coupontarget.CouponTargetAddRequestDto;
import com.nhnacademy.boostorenginx.entity.CouponPolicy;
import com.nhnacademy.boostorenginx.entity.CouponTarget;
import com.nhnacademy.boostorenginx.error.CouponPolicyException;
import com.nhnacademy.boostorenginx.error.NotFoundCouponPolicyException;
import com.nhnacademy.boostorenginx.repository.CouponPolicyRepository;
import com.nhnacademy.boostorenginx.repository.CouponTargetRepository;
import com.nhnacademy.boostorenginx.service.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponTargetRepository couponTargetRepository;

    @Override
    public Long createCouponPolicy(CouponPolicySaveRequestDto requestDto) {
        if (requestDto == null) {
            throw new CouponPolicyException("requestDto is null");
        }

        CouponPolicy couponPolicy = couponPolicyRepository.save(requestDto.toEntity());

        return couponPolicy.getId();
    }

    @Override
    public Optional<CouponPolicy> findByName(CouponPolicyNameRequestDto requestDto) {
        if (requestDto == null) {
            throw new CouponPolicyException("requestDto is null");
        }

        return couponPolicyRepository.findByName(requestDto.couponPolicyName());
    }

    @Override
    public Optional<CouponPolicy> findById(CouponPolicyIdRequestDto requestDto) {
        if (requestDto == null) {
            throw new CouponPolicyException("requestDto is null");
        }

        return couponPolicyRepository.findById(requestDto.couponPolicyId());
    }

    @Override
    public void addTargetToPolicy(CouponTargetAddRequestDto requestDto) {
        if (requestDto == null) {
            throw new CouponPolicyException("requestDto is null");
        }

        CouponPolicy couponPolicy = couponPolicyRepository.findById(requestDto.policyId()).orElseThrow(
                () -> new NotFoundCouponPolicyException("잘못된 쿠폰정책 ID: " + requestDto.policyId()));

        CouponTarget couponTarget = new CouponTarget();
        couponTarget.setTargetId(requestDto.targetId());
        couponPolicy.addCouponTarget(couponTarget);

        couponTargetRepository.save(couponTarget);
    }
}
