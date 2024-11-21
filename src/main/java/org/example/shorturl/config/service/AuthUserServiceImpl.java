package org.example.shorturl.config.service;

import lombok.RequiredArgsConstructor;
import org.example.shorturl.config.mappers.AuthUserMapper;
import org.example.shorturl.config.security.JwtTokenUtil;
import org.example.shorturl.dtos.auth.AuthUserCreateDto;
import org.example.shorturl.dtos.auth.GenerateTokenRequest;
import org.example.shorturl.entity.AuthUser;
import org.example.shorturl.entity.AuthUserOtp;
import org.example.shorturl.repository.AuthUserRepository;
import org.example.shorturl.utils.MailSenderService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserMapper authUserMapper;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MailSenderService mailSenderService;
    private final AuthUserOtpService authUserOtpService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(@NonNull AuthUserCreateDto dto) {
        AuthUser authUser = authUserMapper.toEntity(dto);
        if(authUserRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email already Taken");
        }
        if(authUserRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("Username already Taken");
        }
        authUser.setRole("USER");
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUserRepository.save(authUser);
        AuthUserOtp authUserOtp = authUserOtpService.createOTP(authUser);
        Map<String,String> model = new HashMap<>();
        model.put("to",authUser.getEmail());
        model.put("code",authUserOtp.getCode());
        mailSenderService.sendActivationMail(model);
        return "Success";
    }

    @Override
    public String generateToken(@NonNull GenerateTokenRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(username);
    }



}
