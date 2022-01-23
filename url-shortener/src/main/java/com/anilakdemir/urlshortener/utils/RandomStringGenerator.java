package com.anilakdemir.urlshortener.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RandomStringGenerator {

    @Value("${codeLength}")
    private int codeLength;

    public String generateRandomString(){
        StringBuilder generated= new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        List<Character> letters =alphabet.chars().mapToObj(x->(char)x).collect(Collectors.toList());
        Collections.shuffle(letters);

        for (int i = 0; i < codeLength; i++) {
            generated.append(letters.get(secureRandom.nextInt(letters.size())));
        }

        return generated.toString();
    }
}
