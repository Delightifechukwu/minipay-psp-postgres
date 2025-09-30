
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "settlement_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementBatch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "settlement_ref", unique = true, nullable = false)
    private UUID settlementRef;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    private Instant periodStart;
    private Instant periodEnd;
    private Integer count;
    private BigDecimal transactionAmount;
    private BigDecimal msc;
    private BigDecimal vatAmount;
    private BigDecimal processorFee;
    private BigDecimal processorVat;
    private BigDecimal income;
    private BigDecimal payableVat;
    private BigDecimal amountPayable;
    private String status;
    private Instant createdAt;
}
