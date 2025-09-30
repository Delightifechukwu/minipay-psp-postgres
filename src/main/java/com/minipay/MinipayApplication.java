
package com.minipay;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.minipay.model.User;
import com.minipay.model.Merchant;
import com.minipay.repository.UserRepository;
import com.minipay.repository.MerchantRepository;

import java.util.Set;
import java.time.Instant;

@SpringBootApplication
public class MinipayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinipayApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepo, MerchantRepository merchantRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                userRepo.save(User.builder()
                        .username("admin")
                        .passwordHash("$2a$10$devplaceholder") // change in prod
                        .email("admin@example.com")
                        .status("ACTIVE")
                        .roles(Set.of("ADMIN"))
                        .createdAt(Instant.now())
                        .build());
            }
            if (merchantRepo.count() == 0) {
                merchantRepo.save(Merchant.builder()
                        .merchantId("MERCH-001")
                        .name("Test Merchant")
                        .email("merchant@example.com")
                        .status("ACTIVE")
                        .settlementAccount("1234567890")
                        .settlementBank("Test Bank")
                        .callbackUrl("https://example.com/webhook")
                        .webhookSecret("change-me")
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build());
            }
        };
    }
}
