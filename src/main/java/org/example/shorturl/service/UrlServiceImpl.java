package org.example.shorturl.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.shorturl.mappers.UrlMapper;
import org.example.shorturl.config.security.SessionUser;
import org.example.shorturl.config.security.UserDetails;
import org.example.shorturl.dtos.url.DailyReport;
import org.example.shorturl.dtos.url.UrlReport;
import org.example.shorturl.dtos.url.WeaklyReport;
import org.example.shorturl.entity.Url;
import org.example.shorturl.repository.UrlRepository;
import org.example.shorturl.utils.BaseUtils;
import org.example.shorturl.utils.MailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlMapper urlMapper;
    private final BaseUtils baseUtils;
    private final SessionUser sessionUser;
    private final UrlRepository urlRepository;
    private final MailSenderService mailSenderService;

    @Override
    public Url shortenUrl(@NonNull UrlCreateDto dto) {
        Url url = urlMapper.toEntity(dto);
        url.setCode(baseUtils.shortenUrlCode(sessionUser.id()));
        if (url.getExpiresAt() == null)
            url.setExpiresAt(LocalDateTime.now().plusDays(1));
        return urlRepository.save(url);
    }

    @Override
    public Url getByCode(@NonNull String code) {
        Url url = urlRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Url By Code not found"));
        if(url.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Shortened Url Expired");
        return url;
    }

    @Override
    public Page<Url> getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdAt"));
        return urlRepository.findAllByUserID(sessionUser.id(),pageable);
    }

    @Override
    public WeaklyReport getWeaklyReport() {
        LocalDateTime from = LocalDateTime.now().minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDateTime to = LocalDateTime.now().minusWeeks(1).with(DayOfWeek.SUNDAY);
        List<Url> urls = urlRepository.findAllByUser(sessionUser.id(), from, to);
        AtomicInteger count = new AtomicInteger(0);

        List<DailyReport> dailyReports = new ArrayList<>();
        urls.stream()
                .map(UrlReport::new)
                .collect(Collectors.groupingBy(urlReport -> urlReport.getDayOfWeek().getValue()))
                .forEach((dayNumber, urlsReports) -> {
                    dailyReports.add(new DailyReport(dayNumber, urlsReports));
                    count.addAndGet(urlsReports.size());
                });
        return new WeaklyReport(
                baseUtils.format(from),
                baseUtils.format(to),
                dailyReports,
                count.get()
        );
    }

    @Override
    public void sendWeaklyReport() {
        WeaklyReport weaklyReport = getWeaklyReport();
        UserDetails user = sessionUser.user();
        String email = user.getEmail();
        Map<String, Object> model = Map.of("report", weaklyReport,
                "to", email,
                "username", user.getUsername()
        );
        mailSenderService.sendWeaklyReport(model);
    }
}
