//package com.doinb.redis.lock;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//
///**
// * <p>hsaf redis 配置类</p> 启用redis锁时启用
// */
//@Configuration
//@ConditionalOnClass({JedisConnectionFactory.class})
//public class RedisConfiguration {
//    @Configuration
//    @ConditionalOnProperty(name = "redis.deployType", havingValue = "1", matchIfMissing = true)
//    @ConditionalOnClass({DistributedRedisStandaloneConfiguration.class, DistributedJedisClientConfiguration.class})
//    @ImportResource("classpath:redis/jedis-standalone.xml")
//    static class StandaloneConfiguration {
//    }
//}
