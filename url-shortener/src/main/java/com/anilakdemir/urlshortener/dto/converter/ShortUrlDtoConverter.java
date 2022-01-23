package com.anilakdemir.urlshortener.dto.converter;

import com.anilakdemir.urlshortener.dto.ShortUrlDto;
import com.anilakdemir.urlshortener.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShortUrlDtoConverter {

    public ShortUrlDto convertToDto(ShortUrl shortUrl){
        return ShortUrlDto.builder()
                .id(shortUrl.getId())
                .url(shortUrl.getUrl())
                .code(shortUrl.getCode())
                .build();
    }

    public List<ShortUrlDto> convertToDto(List<ShortUrl> shortUrls){
        return shortUrls.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
