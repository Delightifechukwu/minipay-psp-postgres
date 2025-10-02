package com.minipay.controller;
import com.minipay.model.Merchant;
import com.minipay.model.dto.PaymentRequest;
import com.minipay.model.Payment;
import com.minipay.repository.MerchantRepository;
import com.minipay.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository repo;
    private final MerchantRepository merchantRepository;

    @PostMapping
    @PreAuthorize("hasRole('MERCHANT_USER')")
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequest request) {
        Merchant merchant = merchantRepository.findByMerchantId(request.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        Payment payment = Payment.builder()
                .merchant(merchant)
                .customerId(request.getCustomerEmail())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .createdAt(Instant.now())
                .build();
        return ResponseEntity.ok(repo.save(payment));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Payment> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        return repo.findAll(PageRequest.of(page, size));
    }
}
