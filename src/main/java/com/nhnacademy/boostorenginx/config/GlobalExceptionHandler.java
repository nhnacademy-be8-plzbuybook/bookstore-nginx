package com.nhnacademy.boostorenginx.config;


import com.nhnacademy.boostorenginx.error.NotFoundCouponException;
import com.nhnacademy.boostorenginx.error.NotFoundCouponPolicyException;
import com.nhnacademy.boostorenginx.error.NotFoundMemberCouponException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // NotFoundCouponPolicyException 처리 -> 404 에러
    @ExceptionHandler(NotFoundCouponPolicyException.class)
    public ResponseEntity<String> handleNotFoundCouponPolicyException(NotFoundCouponPolicyException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // NotFoundMemberCouponException 처리 -> 404 에러
    @ExceptionHandler(NotFoundMemberCouponException.class)
    public ResponseEntity<String> handleNotFoundMemberCouponException(NotFoundMemberCouponException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // NotFoundCouponException 처리 -> 404 에러
    @ExceptionHandler(NotFoundCouponException.class)
    public ResponseEntity<String> handleNotFoundCouponException(NotFoundCouponException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
