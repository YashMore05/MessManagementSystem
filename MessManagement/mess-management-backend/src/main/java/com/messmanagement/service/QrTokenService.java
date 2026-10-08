package com.messmanagement.service;

import com.messmanagement.enums.MealType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class QrTokenService {
    @Value("${app.jwt.secret}")
    private String secret;

    public String createToken(Long studentId, Long messId, MealType mealType, LocalDate date) {
        String payload = studentId + ":" + messId + ":" + mealType + ":" + date;
        return Base64.getUrlEncoder().encodeToString((payload + ":" + sign(payload)).getBytes(StandardCharsets.UTF_8));
    }

    public String createBookingToken(Long bookingId, Long studentId, Long messId, MealType mealType, LocalDate date) {
        String payload = "BOOKING:" + bookingId + ":" + studentId + ":" + messId + ":" + mealType + ":" + date;
        return Base64.getUrlEncoder().encodeToString((payload + ":" + sign(payload)).getBytes(StandardCharsets.UTF_8));
    }

    public ParsedQr parseAndValidate(String token) {
        String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
        String[] parts = decoded.split(":");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid QR token");
        }
        String payload = parts[0] + ":" + parts[1] + ":" + parts[2] + ":" + parts[3];
        if (!sign(payload).equals(parts[4])) {
            throw new IllegalArgumentException("QR token signature mismatch");
        }
        LocalDate date = LocalDate.parse(parts[3]);
        if (!LocalDate.now().equals(date)) {
            throw new IllegalArgumentException("QR token expired");
        }
        return new ParsedQr(Long.valueOf(parts[0]), Long.valueOf(parts[1]), MealType.valueOf(parts[2]), date);
    }

    public ParsedBookingQr parseBookingAndValidate(String token) {
        String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
        String[] parts = decoded.split(":");
        if (parts.length != 7 || !"BOOKING".equals(parts[0])) {
            throw new IllegalArgumentException("Invalid booking QR token");
        }
        String payload = parts[0] + ":" + parts[1] + ":" + parts[2] + ":" + parts[3] + ":" + parts[4] + ":" + parts[5];
        if (!sign(payload).equals(parts[6])) {
            throw new IllegalArgumentException("QR token signature mismatch");
        }
        LocalDate date = LocalDate.parse(parts[5]);
        if (!LocalDate.now().equals(date)) {
            throw new IllegalArgumentException("QR token expired");
        }
        return new ParsedBookingQr(Long.valueOf(parts[1]), Long.valueOf(parts[2]), Long.valueOf(parts[3]), MealType.valueOf(parts[4]), date);
    }

    private String sign(String payload) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((payload + secret).getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not sign QR token", ex);
        }
    }

    public record ParsedQr(Long studentId, Long messId, MealType mealType, LocalDate date) {
    }

    public record ParsedBookingQr(Long bookingId, Long studentId, Long messId, MealType mealType, LocalDate date) {
    }
}
