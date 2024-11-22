package org.example.shorturl.service;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.example.shorturl.entity.Url}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlCreateDto implements Serializable {
    private String url;
    private String description;
    private LocalDateTime expiresAt;

}