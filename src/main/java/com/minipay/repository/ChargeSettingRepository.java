
package com.minipay.repository;

import com.minipay.model.ChargeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import com.minipay.model.Merchant;
import java.util.Optional;

public interface ChargeSettingRepository extends JpaRepository<ChargeSetting, Long> {
    Optional<ChargeSetting> findByMerchant(Merchant merchant);
}
