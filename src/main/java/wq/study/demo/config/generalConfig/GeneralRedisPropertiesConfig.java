package wq.study.demo.config.generalConfig;//package com.example.demo.config.generalConfig;
//
//import com.alibaba.druid.util.StringUtils;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
////临时屏蔽
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis.general-config")
//public class GeneralRedisPropertiesConfig {
//
//    private int minIdle;
//    private int maxIdle;
//    private int maxTotal;
//    private int maxWait;
//    private boolean testOnBorrow;
//    private boolean blockWhenExhausted;
//    private boolean testOnReturn;
//    private boolean testOnCreate;
//    private boolean testWhileIdle;
//    private int timeBetweenEvictionRunsMillis;
//    private int minEvictableIdleTimeMillis;
//    private int numTestsPerEvictionRun;
//
//    @Bean(name = "redisClientConfiguration")
//    public JedisClientConfiguration getRedisPoolConfiguration() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMinIdle(minIdle);
//        poolConfig.setMaxIdle(maxIdle);
//        poolConfig.setMaxTotal(maxTotal);
//        poolConfig.setMaxWaitMillis(maxWait);
//        poolConfig.setTestOnBorrow(testOnBorrow);
//        poolConfig.setBlockWhenExhausted(blockWhenExhausted);
//        poolConfig.setTestOnReturn(testOnReturn);
//        poolConfig.setTestOnCreate(testOnCreate);
//        poolConfig.setTestWhileIdle(testWhileIdle);
//        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
//        return JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig).build();
//    }
//
//    public void setRedisSerializeMethod(RedisTemplate<String,Object> redisTemplate) {
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//    }
//
//    public RedisStandaloneConfiguration generateRedisConfig(String hostName, int port, int database, String password) {
//        RedisPassword redisPassword;
//        if (StringUtils.isEmpty(password) || StringUtils.equalsIgnoreCase("none",password)) {
//            redisPassword = RedisPassword.none();
//        }else {
//            redisPassword = RedisPassword.of(password);
//        }
//        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(hostName,port);
//        standaloneConfiguration.setDatabase(database);
//        standaloneConfiguration.setPassword(redisPassword);
//        return standaloneConfiguration;
//    }
//
//    public int getMinIdle() {
//        return minIdle;
//    }
//
//    public void setMinIdle(int minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public int getMaxIdle() {
//        return maxIdle;
//    }
//
//    public void setMaxIdle(int maxIdle) {
//        this.maxIdle = maxIdle;
//    }
//
//    public int getMaxTotal() {
//        return maxTotal;
//    }
//
//    public void setMaxTotal(int maxTotal) {
//        this.maxTotal = maxTotal;
//    }
//
//    public int getMaxWait() {
//        return maxWait;
//    }
//
//    public void setMaxWait(int maxWait) {
//        this.maxWait = maxWait;
//    }
//
//    public boolean isTestOnBorrow() {
//        return testOnBorrow;
//    }
//
//    public void setTestOnBorrow(boolean testOnBorrow) {
//        this.testOnBorrow = testOnBorrow;
//    }
//
//    public boolean isBlockWhenExhausted() {
//        return blockWhenExhausted;
//    }
//
//    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
//        this.blockWhenExhausted = blockWhenExhausted;
//    }
//
//    public boolean isTestOnReturn() {
//        return testOnReturn;
//    }
//
//    public void setTestOnReturn(boolean testOnReturn) {
//        this.testOnReturn = testOnReturn;
//    }
//
//    public boolean isTestOnCreate() {
//        return testOnCreate;
//    }
//
//    public void setTestOnCreate(boolean testOnCreate) {
//        this.testOnCreate = testOnCreate;
//    }
//
//    public boolean isTestWhileIdle() {
//        return testWhileIdle;
//    }
//
//    public void setTestWhileIdle(boolean testWhileIdle) {
//        this.testWhileIdle = testWhileIdle;
//    }
//
//    public int getTimeBetweenEvictionRunsMillis() {
//        return timeBetweenEvictionRunsMillis;
//    }
//
//    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
//        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
//    }
//
//    public int getMinEvictableIdleTimeMillis() {
//        return minEvictableIdleTimeMillis;
//    }
//
//    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
//        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
//    }
//
//    public int getNumTestsPerEvictionRun() {
//        return numTestsPerEvictionRun;
//    }
//
//    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
//        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
//    }
//}
