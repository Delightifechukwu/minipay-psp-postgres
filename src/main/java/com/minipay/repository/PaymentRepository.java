
package com.minipay.repository;

import com.minipay.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMerchant_MerchantId(String merchantId);
}
