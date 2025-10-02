
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payment_ref", unique = true, nullable = false)
    private UUID paymentRef;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private String channel;
    @Enumerated(EnumType.STRING)
    private String status;
    private BigDecimal msc;
    private BigDecimal vatAmount;
    private BigDecimal processorFee;
    private BigDecimal processorVat;
    private BigDecimal payableVat;
    private BigDecimal amountPayable;
    private String customerId;
    private Instant createdAt;
    private Instant updatedAt;
    public enum Status {
        PENDING, SUCCESS, FAILED
    }

    public enum Channel {
        CARD, WALLET, BANK_TRANSFER
    }
}
