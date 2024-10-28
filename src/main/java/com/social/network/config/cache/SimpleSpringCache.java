package com.social.network.config.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

@EnableCaching
@Configuration
@ConditionalOnProperty(name = "cache.simple.enable", havingValue = "true")
//@ConditionalOnMissingBean(name = "cacheManager")
@Slf4j
public class SimpleSpringCache
{

	@Bean
	@Primary
	public CacheManager cacheManager()
	{
		log.debug("...####Simple cache Bean is created... Because Redis CacheManager Bean was missing####....");
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		List<Cache> caches = new ArrayList<>();
		caches.add(new ConcurrentMapCache("personCache"));
		cacheManager.setCaches(caches);
		return cacheManager;
	}

}
