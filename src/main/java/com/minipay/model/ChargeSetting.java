
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "charge_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeSetting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    private BigDecimal percentageFee;
    private BigDecimal fixedFee;
    @Column(name = "use_fixed_msc")
    private Boolean useFixedMSC;
    private BigDecimal mscCap;
    private BigDecimal vatRate;
    private BigDecimal platformProviderRate;
    private BigDecimal platformProviderCap;
    private Instant createdAt;
    private Instant updatedAt;
}
