package org.example.shorturl.config.service;

import lombok.NonNull;
import org.example.shorturl.entity.AuthUser;
import org.example.shorturl.entity.AuthUserOtp;

public interface AuthUserOtpService {

    AuthUserOtp create(@NonNull AuthUserOtp authUserOtp);

    AuthUserOtp createOTP(@NonNull AuthUser authUser);
}
