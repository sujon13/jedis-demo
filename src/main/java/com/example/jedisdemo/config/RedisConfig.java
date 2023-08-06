package com.example.jedisdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${redis.server.url:localhost}")
    private String redisHost;

    @Value("${redis.server.port:6379}")
    private int redisPort;

    @Value("${redis.server.password:none}")
    private String redisPassword;

    @Value("${redis.server.total_connection:64}")
    private int maxConnection;

    @Value("${redis.server.idle.connection.max:32}")
    private int maxIdleConnection;

    @Value("${redis.server.idle.connection.min:16}")
    private int minIdleConnection;

    private void printValues() {
        log.info("redisHost : {}", redisHost);
        log.info("redisPort : {}", redisPort);
        log.info("redisPassword : {}", redisPassword);
        log.info("maxConnection : {}", maxConnection);
        log.info("maxIdleConnection : {}", maxIdleConnection);
        log.info("minIdleConnection : {}", minIdleConnection);
    }

    @Bean
    public JedisClientConfiguration getJedisClientConfiguration() {
        printValues();

        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(maxConnection);
        poolConfig.setMaxIdle(maxIdleConnection);
        poolConfig.setMinIdle(minIdleConnection);

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder builder =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        return builder.poolConfig(poolConfig).build();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        if (!redisPassword.equals("none")) {
            redisStandaloneConfiguration.setPassword(redisPassword);
        }
        return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
