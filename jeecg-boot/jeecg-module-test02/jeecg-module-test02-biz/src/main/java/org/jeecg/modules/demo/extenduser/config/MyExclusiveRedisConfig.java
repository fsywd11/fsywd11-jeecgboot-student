package org.jeecg.modules.demo.extenduser.config;


import io.lettuce.core.api.StatefulConnection;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 完全自定义的Redis配置类（类名唯一，无任何Bean冲突）
 * 手动创建LettuceConnectionFactory，解决Bean缺失问题
 */
@Configuration
public class MyExclusiveRedisConfig {

    // 从配置文件读取Redis参数（与你Nacos/本地配置保持一致）
    @Value("${spring.redis.host:127.0.0.1}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.database:0}")
    private int redisDatabase;

    @Value("${spring.redis.lettuce.pool.max-active:8}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle:8}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle:2}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait:3000}")
    private long maxWait;

    /**
     * 手动创建LettuceConnectionFactory Bean（核心：解决缺失问题）
     * 该Bean名称自动为lettuceConnectionFactory，与框架依赖的名称一致
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        // 1. Redis单机配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setPassword(redisPassword);
        redisConfig.setDatabase(redisDatabase);

        // 2. Lettuce连接池配置
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);

        // 3. 构建Lettuce客户端配置
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig((GenericObjectPoolConfig<StatefulConnection<?, ?>>) poolConfig)
                .build();

        // 4. 创建并返回连接工厂
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * 自定义RedisTemplate（可选，与框架不冲突，满足业务缓存需求）
     * 若框架已有redisTemplate，此Bean可指定唯一名称，或直接覆盖（建议保留，序列化更友好）
     */
    @Bean
    public RedisTemplate<String, Object> myExclusiveRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // 序列化配置（解决默认JDK序列化乱码问题）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // Key和HashKey使用String序列化
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Value和HashValue使用JSON序列化
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
