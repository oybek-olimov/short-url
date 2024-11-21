package org.example.shorturl.config.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.shorturl.entity.AuthUser;
import org.example.shorturl.entity.AuthUserOtp;
import org.example.shorturl.repository.AuthUserOtpRepository;
import org.example.shorturl.repository.AuthUserRepository;
import org.example.shorturl.utils.BaseUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthUserOtpServiceImpl implements AuthUserOtpService {

    private final AuthUserOtpRepository authUserOtpRepository;
    private final BaseUtils utils;
    @Override
    public AuthUserOtp create(@NonNull AuthUserOtp authUserOtp) {
        return authUserOtpRepository.save(authUserOtp);
    }

    @Override
    public AuthUserOtp createOTP(@NonNull AuthUser authUser) {
        AuthUserOtp authUserOtp = AuthUserOtp.builder()
                .code(utils.generateOtp(authUser.getId()))
                .userId(authUser.getId())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        return create(authUserOtp);
    }
}
