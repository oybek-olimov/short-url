package org.example.shorturl.config.security;

import lombok.RequiredArgsConstructor;
import org.example.shorturl.entity.AuthUser;
import org.example.shorturl.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final AuthUserRepository authUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found: " + username));
        return new UserDetails(authUser);
    }
}