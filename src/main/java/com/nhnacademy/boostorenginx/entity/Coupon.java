package com.nhnacademy.boostorenginx.entity;

import com.nhnacademy.boostorenginx.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id; // 쿠폰 ID

    @Column(unique = true, nullable = false)
    private String code; // 쿠폰코드

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 쿠폰상태

    @Column(nullable = false)
    private LocalDateTime issuedAt; // 쿠폰발급일자

    @Column(nullable = false)
    private LocalDateTime expiredAt; // 쿠폰만료일자

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy; // 쿠폰정책(N:1)

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CouponHistory> couponHistoryList = new ArrayList<>(); // 쿠폰변경이력(1:N, 복합키)

    @OneToOne(mappedBy = "coupon")
    private MemberCoupon memberCoupon; // 쿠폰정책(1:1)

    public Coupon(Status status, LocalDateTime issuedAt, LocalDateTime expiredAt, CouponPolicy couponPolicy) {
        this.code = UUID.randomUUID().toString();
        this.status = status;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.couponPolicy = couponPolicy; // 쿠폰정책으로 부터 쿠폰을 생성함 -> Dto 에 담겨있어야 되는 단점이있음
    }

    // status 속성이 변경될때 외부에서 호출하는 메서드
    public void changeStatus(Status newStatus, LocalDateTime changeDate, String reason) {
        if (this.status != newStatus) {
            this.status = newStatus;
            this.addHistory(newStatus, changeDate, reason);
        }
    }

    // 쿠폰상태(status) 가 변경될때마다 호출
    public void addHistory(Status status, LocalDateTime changeDate, String reason) {
        CouponHistory history = CouponHistory.builder()
                .coupon(this)
                .status(status)
                .changeDate(changeDate)
                .reason(reason)
                .build();

        couponHistoryList.add(history);
    }

}
