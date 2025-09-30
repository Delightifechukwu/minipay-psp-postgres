
package com.minipay.repository;

import com.minipay.model.SettlementBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SettlementBatchRepository extends JpaRepository<SettlementBatch, Long> {
    List<SettlementBatch> findByMerchant_MerchantId(String merchantId);
}
