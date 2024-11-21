package org.example.shorturl.repository;

import org.example.shorturl.entity.AuthUserOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserOtpRepository extends JpaRepository<AuthUserOtp, Long> {
}