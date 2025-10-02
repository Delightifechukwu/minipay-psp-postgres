package com.minipay.util;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
public class WebhookUtil {
    public static String generateSignature(String payload, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error while generating webhook signature", e);
        }
    }

    public static boolean verifySignature(String payload, String secret, String signature) {
        String expected = generateSignature(payload, secret);
        return expected.equals(signature);
    }
}
