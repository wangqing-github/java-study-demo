package wq.study.demo.config;//package com.example.demo.config;
//
//import com.example.demo.config.generalConfig.GeneralRedisPropertiesConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
//public class LocalServerRedisConfig {
//
//    private String hostName;
//    private int port;
//    private int database;
//    private String password;
//
//    @Autowired
//    private GeneralRedisPropertiesConfig generalRedisPropertiesConfig;
//
//    @Bean(name = "localServerConnectionFactory")
//    public RedisConnectionFactory getRedisConnectionFactory(@Qualifier("redisClientConfiguration") JedisClientConfiguration configuration) {
//        RedisStandaloneConfiguration standaloneConfiguration = generalRedisPropertiesConfig.generateRedisConfig(hostName, port, database, password);
//        return new JedisConnectionFactory(standaloneConfiguration,configuration);
//    }
//
//    @Bean(name = "localServerRedisTemplate")
//    public RedisTemplate<String,Object> getRedisTemplate(@Qualifier("localServerConnectionFactory")RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        connectionFactory.getConnection().ping();
//        generalRedisPropertiesConfig.setRedisSerializeMethod(redisTemplate);
//        return redisTemplate;
//    }
//
//    public void setHostName(String hostName) {
//        this.hostName = hostName;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public void setDatabase(int database) {
//        this.database = database;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//}
