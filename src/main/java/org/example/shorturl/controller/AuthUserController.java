package org.example.shorturl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shorturl.service.AuthUserService;
import org.example.shorturl.dtos.auth.AuthUserCreateDto;
import org.example.shorturl.dtos.auth.GenerateTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthUserService authUserService;

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@Valid @RequestBody GenerateTokenRequest request) {
        return ResponseEntity.ok(authUserService.generateToken(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody AuthUserCreateDto dto) {
        return ResponseEntity.status(201).body(authUserService.register(dto));
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activateAccount(@PathVariable String code){
        return ResponseEntity.ok(authUserService.activateAccount(code));
    }

    @PostMapping("/resend-activation-code")
    public ResponseEntity<String> resendActivationCode(@RequestParam String email) {
        return ResponseEntity.ok(authUserService.resendActivationCode(email));
    }
}
