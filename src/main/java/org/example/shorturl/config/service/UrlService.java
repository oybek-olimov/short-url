package org.example.shorturl.config.service;

import lombok.NonNull;
import org.example.shorturl.dtos.url.WeaklyReport;
import org.example.shorturl.entity.Url;
import org.springframework.data.domain.Page;

public interface UrlService {

    Url shortenUrl(@NonNull UrlCreateDto dto);

    Url getByCode(@NonNull String code);

    Page<Url> getPage(int page, int size);

    WeaklyReport getWeaklyReport();
}
