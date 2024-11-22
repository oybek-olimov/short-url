package org.example.shorturl.controller;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.shorturl.config.service.UrlCreateDto;
import org.example.shorturl.config.service.UrlService;
import org.example.shorturl.config.service.UrlServiceImpl;
import org.example.shorturl.dtos.url.WeaklyReport;
import org.example.shorturl.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/url")
    public ResponseEntity<Url> shortenUrl(@Valid @RequestBody UrlCreateDto dto){
        return ResponseEntity.ok(urlService.shortenUrl(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/url/{code}")
    public ResponseEntity<Url> get(@PathVariable String code){
        return ResponseEntity.ok(urlService.getByCode(code));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/url")
    public ResponseEntity<Page<Url>> getPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(urlService.getPage(page,size));
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public void redirectTo(@PathVariable String code, HttpServletResponse response) throws IOException {
       Url url = urlService.getByCode(code);
       response.sendRedirect(url.getUrl());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/url/report")
    public WeaklyReport getWeaklyReport(){
        return urlService.getWeaklyReport();
    }
}
