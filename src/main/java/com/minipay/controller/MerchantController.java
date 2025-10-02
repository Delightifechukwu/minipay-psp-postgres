package com.minipay.controller;
import com.minipay.model.Merchant;
import com.minipay.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantRepository repo;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Merchant> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return repo.findAll(PageRequest.of(page, size));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Merchant createMerchant(@RequestBody Merchant merchant) {
        return merchant; // just return for now
    }

    // Both ADMIN and MERCHANT_USER can view merchant details
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MERCHANT_USER')")
    public Merchant getMerchant(@PathVariable String id) {
        return new Merchant(); // dummy
    }
}
