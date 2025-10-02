package com.minipay.controller;
import com.minipay.model.Merchant;
import com.minipay.model.SettlementBatch;
import com.minipay.repository.MerchantRepository;
import com.minipay.repository.SettlementBatchRepository;
import com.minipay.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;
    private final SettlementBatchRepository batchRepo;
    private final MerchantRepository merchantRepo;

    @PostMapping("/run/{merchantId}")
    public SettlementBatch runSettlement(
            @PathVariable Long merchantId,
            @RequestParam Instant start,
            @RequestParam Instant end) {
        Merchant merchant = merchantRepo.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        return settlementService.runSettlement(merchant, start, end);
    }

    @GetMapping
    public List<SettlementBatch> list() {
        return batchRepo.findAll();
    }

    @GetMapping("/{id}")
    public SettlementBatch get(@PathVariable Long id) {
        return batchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
