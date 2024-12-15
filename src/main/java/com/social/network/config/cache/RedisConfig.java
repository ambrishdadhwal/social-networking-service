package com.social.network.config.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "cache.redis.enable", havingValue = "true")
@Slf4j
public class RedisConfig
{

	@Value("${cache.redis.host}")
	private String REDIS_HOST;

	@Value("${cache.redis.port}")
	private int REDIS_PORT;

	@Value("${cache.redis.password}")
	private String REDIS_PASSWORD;

	@Bean
	public RedisTemplate<Object, Object> redisTemplate()
	{
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(this.lettuceConnectionFactory());
		return template;
	}

	@Bean
	public CacheManager cacheManager()
	{
		log.debug("...####Redis cache Bean is created####....");
		return RedisCacheManager
			.builder(this.lettuceConnectionFactory())
			.cacheDefaults(this.redisCacheConfiguration())
			.build();
	}

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration()
	{
		log.debug("...####Redis cache Configuration Bean is created####....");
		// Do not change the default object mapper, we need to serialize the class name into the value
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper = objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

		objectMapper.registerModule(new JavaTimeModule());
		return RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofMinutes(30))
			.disableCachingNullValues()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
	}

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory()
	{
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(REDIS_HOST);
		redisConfig.setPort(REDIS_PORT);
		redisConfig.setPassword(REDIS_PASSWORD);
		log.debug("...####Redis cache lettuceConnectionFactory Bean is created####....");
		return new LettuceConnectionFactory(redisConfig);
	}

}
