// src/main/java/com/minipay/model/SettlementItem.java
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "settlement_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private SettlementBatch batch;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    private BigDecimal amount;
    private BigDecimal msc;
    private BigDecimal vatAmount;
    private BigDecimal processorFee;
    private BigDecimal processorVat;
}
