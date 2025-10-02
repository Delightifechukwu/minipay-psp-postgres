package com.minipay.service;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class WebhookService {
    public boolean verifySignature(String payload, String providedSignature, String merchantSecret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(merchantSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            String computedHash = Base64.getEncoder()
                    .encodeToString(sha256_HMAC.doFinal(payload.getBytes()));

            return computedHash.equals(providedSignature);
        } catch (Exception e) {
            return false;
        }
    }
}
