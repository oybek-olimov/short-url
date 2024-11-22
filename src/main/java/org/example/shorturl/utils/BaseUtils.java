package org.example.shorturl.utils;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Component
public class BaseUtils {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Base64.Encoder encoder = Base64.getUrlEncoder();

    public String generateOtp(Long userId){
        return encoder.encodeToString((UUID.randomUUID().toString() + userId).getBytes());
    }

    public String shortenUrlCode(Long userId){
        return Hashing
                .murmur3_32_fixed()
                .hashString(UUID.randomUUID().toString() + userId, StandardCharsets.UTF_8)
                .toString();
    }

    public String format(LocalDateTime localDateTime){
        return FORMATTER.format(localDateTime);
    }


}
