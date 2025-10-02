package com.minipay.controller;

import com.minipay.repository.MerchantRepository;
import com.minipay.service.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/merchant/webhook")
@RequiredArgsConstructor

public class WebhookReceiverController {
    private final WebhookService webhookVerifier;
    private final MerchantRepository merchantRepository;

    @PostMapping("/{merchantId}/webhook")
    public ResponseEntity<String> handleWebhook(
            @PathVariable String merchantId,
            @RequestHeader("X-Signature") String signature,
            @RequestBody String payload) {

        return merchantRepository.findByMerchantId(merchantId)
                .map(merchant -> {
                    boolean valid = webhookVerifier.verifySignature(payload, signature, merchant.getWebhookSecret());
                    if (valid) {
                        return ResponseEntity.ok("Webhook verified for " + merchant.getName() + ": " + payload);
                    } else {
                        return ResponseEntity.badRequest().body("Invalid signature!");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}


