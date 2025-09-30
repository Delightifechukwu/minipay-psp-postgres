
package com.minipay.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "merchants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "merchant_id", unique = true, nullable = false)
    private String merchantId;
    private String name;
    private String email;
    private String status;
    private String settlementAccount;
    private String settlementBank;
    private String callbackUrl;
    private String webhookSecret;
    private Instant createdAt;
    private Instant updatedAt;
}
