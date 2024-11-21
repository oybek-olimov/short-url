package org.example.shorturl.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link org.example.shorturl.entity.AuthUser} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTokenRequest implements Serializable {
    @NotBlank private String username;
    @NotBlank private String password;
}