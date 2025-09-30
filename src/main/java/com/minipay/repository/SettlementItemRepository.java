
package com.minipay.repository;

import com.minipay.model.SettlementItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SettlementItemRepository extends JpaRepository<SettlementItem, Long> {
    List<SettlementItem> findByBatchId(Long batchId);
}
