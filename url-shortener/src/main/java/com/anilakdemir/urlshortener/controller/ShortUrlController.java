package com.anilakdemir.urlshortener.controller;

import com.anilakdemir.urlshortener.dto.ShortUrlDto;
import com.anilakdemir.urlshortener.dto.converter.ShortUrlDtoConverter;
import com.anilakdemir.urlshortener.model.ShortUrl;
import com.anilakdemir.urlshortener.request.ShortUrlRequest;
import com.anilakdemir.urlshortener.request.converter.ShortUrlRequestConverter;
import com.anilakdemir.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ShortUrlController {

    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlDtoConverter shortUrlDtoConverter,
                              ShortUrlRequestConverter shortUrlRequestConverter,
                              ShortUrlService shortUrlService) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.shortUrlService = shortUrlService;
    }

    @GetMapping
    public ResponseEntity<List<ShortUrlDto>> getAllUrls(){
        return ResponseEntity.ok()
                .body(shortUrlDtoConverter.convertToDto(shortUrlService.getAllShortUrls()));
    }

    @GetMapping("/show/{code}")
    public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @PathVariable(name = "code") String code){
        return ResponseEntity.ok()
                .body(shortUrlDtoConverter.convertToDto(shortUrlService.getUrlByCode(code)));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable(name = "code") String code) throws URISyntaxException {
        ShortUrl shortUrl = shortUrlService.getUrlByCode(code);
        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(httpHeaders).build();
    }

    @PostMapping
    public ResponseEntity<ShortUrlDto> create(@Valid @RequestBody ShortUrlRequest shortUrlRequest){
        ShortUrl shortUrl = shortUrlRequestConverter.convertToEntity(shortUrlRequest);
        return new ResponseEntity<>(shortUrlDtoConverter.convertToDto(shortUrlService.create(shortUrl)), HttpStatus.CREATED);
    }

}
