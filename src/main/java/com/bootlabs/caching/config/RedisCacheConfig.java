package com.bootlabs.caching.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
@EnableCaching // Enables Spring's caching capability, allowing annotations like @Cacheable, @CacheEvict, and @CachePut to work.
public class RedisCacheConfig {

    @Bean // Defines a Spring bean for RedisCacheManager
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Define cache configuration
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // Prevent caching of null values
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(30));

        // Create and return a RedisCacheManager with the specified configuration
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}