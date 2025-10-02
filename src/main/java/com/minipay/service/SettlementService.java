package com.minipay.service;
import com.minipay.model.*;
import com.minipay.repository.PaymentRepository;
import com.minipay.repository.SettlementBatchRepository;
import com.minipay.repository.SettlementItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final PaymentRepository paymentRepo;
    private final SettlementBatchRepository batchRepo;
    private final SettlementItemRepository itemRepo;

    public SettlementBatch runSettlement(Merchant merchant, Instant start, Instant end) {
        List<Payment> successfulPayments = paymentRepo.findByMerchantAndStatusAndCreatedAtBetween(
                merchant, Payment.Status.SUCCESS, start, end);

        BigDecimal totalAmount = successfulPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SettlementBatch batch = SettlementBatch.builder()
                .settlementRef(UUID.randomUUID().toString())
                .merchant(merchant)
                .periodStart(start)
                .periodEnd(end)
                .count(successfulPayments.size())
                .transactionAmount(totalAmount)
                .status(SettlementBatch.Status.PENDING)
                .createdAt(Instant.now())
                .build();

        batch = batchRepo.save(batch);

        for (Payment p : successfulPayments) {
            SettlementItem item = SettlementItem.builder()
                    .batch(batch)
                    .payment(p)
                    .amount(p.getAmount())
                    .msc(p.getMsc())
                    .vatAmount(p.getVatAmount())
                    .processorFee(p.getProcessorFee())
                    .processorVat(p.getProcessorVat())
                    .build();
            itemRepo.save(item);
        }

        return batch;
    }
}
