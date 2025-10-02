
package com.minipay;

import com.minipay.model.Role;
import com.minipay.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.minipay.model.User;
import com.minipay.model.Merchant;
import com.minipay.repository.UserRepository;
import com.minipay.repository.MerchantRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.time.Instant;

@SpringBootApplication
public class MinipayApplication {

    public static void main(String[] args) {

        SpringApplication.run(MinipayApplication.class, args);
        System.out.println("**********Application Started**********");
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepo,
                             RoleRepository roleRepo,
                             MerchantRepository merchantRepo) {
        return args -> {
            Role adminRole = roleRepo.findByNameWithUsers("ADMIN")
                    .orElseGet(() -> roleRepo.save(Role.builder().name("ADMIN").build()));

            if (userRepo.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .passwordHash(new BCryptPasswordEncoder().encode("admin123")) // change in prod
                        .email("admin@example.com")
                        .status(User.Status.ACTIVE)
                        .createdAt(Instant.now())
                        .roles(Set.of(adminRole))
                        .build();
                userRepo.save(admin);
            }

            if (merchantRepo.count() == 0) {
                merchantRepo.save(Merchant.builder()
                        .merchantId("MERCH-001")
                        .name("Demo Merchant")
                        .email("merchant@example.com")
                        .status("ACTIVE")
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build());
            }
        };
    }


}
