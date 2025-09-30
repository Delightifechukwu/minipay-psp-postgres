
package com.minipay.repository;

import com.minipay.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantId(String merchantId);
}
