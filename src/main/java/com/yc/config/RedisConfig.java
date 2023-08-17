package com.yc.config;

import com.yc.bean.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 */
//@Configuration
public class RedisConfig{
//    @Bean
//    public RedisTemplate<String, Account>redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate<String,Account>template=new RedisTemplate<>();
//        //关联
//        template.setConnectionFactory(factory);
//        //设置key的序列化器
//        template.setKeySerializer(new StringRedisSerializer());
//        //设置value的序列化器
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Account.class));
//        return template;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object>template=new RedisTemplate<>();
        //关联
        template.setConnectionFactory(factory);
        //设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        //设置value的序列化器
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Account.class));
        return template;
    }
}

