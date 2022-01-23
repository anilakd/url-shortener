package com.anilakdemir.urlshortener.service;

import com.anilakdemir.urlshortener.exception.CodeAlreadyExistsException;
import com.anilakdemir.urlshortener.exception.ShortUrlNotFoundException;
import com.anilakdemir.urlshortener.model.ShortUrl;
import com.anilakdemir.urlshortener.repository.ShortUrlRepository;
import com.anilakdemir.urlshortener.utils.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ShortUrlService {
    
    private final ShortUrlRepository shortUrlRepository;
    private final RandomStringGenerator randomStringGenerator;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, RandomStringGenerator randomStringGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.randomStringGenerator = randomStringGenerator;
    }


    public List<ShortUrl> getAllShortUrls() {
        return shortUrlRepository.findAll();
    }

    public ShortUrl getUrlByCode(String code) {
        return shortUrlRepository.findByCode(code)
                .orElseThrow(
                        ()-> new ShortUrlNotFoundException("url not found")
                );
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if(shortUrl.getCode()==null || shortUrl.getCode().isEmpty()){
            shortUrl.setCode(generateCode());
        }else if(shortUrlRepository.findByCode(shortUrl.getCode()).isPresent()){
            throw  new CodeAlreadyExistsException("code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase(Locale.ENGLISH));
        return shortUrlRepository.save(shortUrl);
    }

    private String generateCode() {
        String code;

        do {
            code = randomStringGenerator.generateRandomString();
        }while (shortUrlRepository.findByCode(code).isPresent());

        return code;
    }
}
