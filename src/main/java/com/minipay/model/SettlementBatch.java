// src/main/java/com/minipay/model/SettlementBatch.java
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "settlement_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String settlementRef; // UUID

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    private Instant periodStart;
    private Instant periodEnd;

    private int count;
    private BigDecimal transactionAmount;
    private BigDecimal msc;
    private BigDecimal vatAmount;
    private BigDecimal processorFee;
    private BigDecimal processorVat;
    private BigDecimal income;
    private BigDecimal payableVat;
    private BigDecimal amountPayable;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant createdAt;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementItem> items;

    public enum Status {
        PENDING, POSTED
    }
}
