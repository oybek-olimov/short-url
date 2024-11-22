package org.example.shorturl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Url extends Auditable{

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private String url;

    private String description;

    @Future
    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
