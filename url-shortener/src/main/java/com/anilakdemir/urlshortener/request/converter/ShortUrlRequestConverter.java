package com.anilakdemir.urlshortener.request.converter;

import com.anilakdemir.urlshortener.model.ShortUrl;
import com.anilakdemir.urlshortener.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest shortUrlRequest){
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode())
                .build();
    }


}
