package org.example.shorturl.service;

import lombok.NonNull;
import org.example.shorturl.entity.AuthUser;
import org.example.shorturl.entity.AuthUserOtp;

public interface AuthUserOtpService {

    AuthUserOtp create(@NonNull AuthUserOtp authUserOtp);

    AuthUserOtp createOTP(@NonNull AuthUser authUser);
}
