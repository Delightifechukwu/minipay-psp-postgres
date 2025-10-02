package com.minipay.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class PaymentRequest {
    @NotNull
    private String merchantId;
    @NotNull
    private String customerEmail;
    @Positive
    private BigDecimal amount;
    @NotNull
    private String currency;
}
