package com.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistService {

    private final CacheManager cacheManager;

    @Cacheable(value = "tokenBlacklist", key = "#token")
    public void blacklist(String token) {
        log.info("Blacklisting token... {}", token);
    }

    public boolean isTokenBlacklisted(String token) {
        return cacheManager.getCacheNames().contains(token);
    }
}
