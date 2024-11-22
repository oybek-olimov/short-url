package org.example.shorturl.service;

import org.example.shorturl.dtos.auth.AuthUserCreateDto;
import org.example.shorturl.dtos.auth.GenerateTokenRequest;
import org.springframework.lang.NonNull;

public interface AuthUserService {
    String register(@NonNull AuthUserCreateDto dto);

    String generateToken(@NonNull GenerateTokenRequest request);

    String activateAccount(@NonNull String code);

    String resendActivationCode(@NonNull String email);

}