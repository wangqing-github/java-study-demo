package wq.study.demo.config.generalConfig;//package com.example.demo.config.generalConfig;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//
//import java.util.List;
//import java.util.Properties;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
//public class GeneralDataBasePropertiesConfig {
//
//    private int maxActive;
//    private int minIdle;
//    private int initialSize;
//    private boolean keepAlive;
//    private String validationQuery;
//    private boolean testWhileIdle;
//    private boolean testOnBorrow;
//    private int maxWait;
//    private int minEvictableIdleTimeMillis;
//    private int maxPoolPreparedStatementPerConnectionSize;
//    private List<String> connectionInitSqls;
//    private boolean poolPreparedStatements;
//    private boolean testOnReturn;
//    private String driverClassName;
//    private Properties connectProperties;
//
//    public NameMatchTransactionAttributeSource getPropagationSource() {
//        DefaultTransactionAttribute defaultAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
//        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
//        readOnlyAttribute.setReadOnly(true);
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//        source.addTransactionalMethod("*", defaultAttribute);
//        source.addTransactionalMethod("save*", defaultAttribute);
//        source.addTransactionalMethod("insert*", defaultAttribute);
//        source.addTransactionalMethod("delete*", defaultAttribute);
//        source.addTransactionalMethod("update*", defaultAttribute);
//        source.addTransactionalMethod("exec*", defaultAttribute);
//        source.addTransactionalMethod("set*", defaultAttribute);
//        source.addTransactionalMethod("add*", defaultAttribute);
//        source.addTransactionalMethod("get*", readOnlyAttribute);
//        source.addTransactionalMethod("query*", readOnlyAttribute);
//        source.addTransactionalMethod("find*", readOnlyAttribute);
//        source.addTransactionalMethod("list*", readOnlyAttribute);
//        source.addTransactionalMethod("count*", readOnlyAttribute);
//        source.addTransactionalMethod("is*", readOnlyAttribute);
//        return source;
//    }
//
//    public void buildDatasource(DruidDataSource druidDataSource, boolean exclude) {
//        druidDataSource.setMaxActive(maxActive);
//        druidDataSource.setMinIdle(minIdle);
//        druidDataSource.setInitialSize(initialSize);
//        druidDataSource.setKeepAlive(keepAlive);
//        druidDataSource.setValidationQuery(validationQuery);
//        druidDataSource.setTestWhileIdle(testWhileIdle);
//        druidDataSource.setTestOnBorrow(testOnBorrow);
//        druidDataSource.setMaxWait(maxWait);
//        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        druidDataSource.setConnectionInitSqls(connectionInitSqls);
//        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
//        druidDataSource.setTestOnReturn(testOnReturn);
//        druidDataSource.setDriverClassName(driverClassName);
//        if (!exclude) druidDataSource.setConnectProperties(connectProperties);
//    }
//
//    public void buildDatasource(DruidDataSource druidDataSource) {
//        buildDatasource(druidDataSource,false);
//    }
//
//    public void setMaxActive(int maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    public void setMinIdle(int minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public void setInitialSize(int initialSize) {
//        this.initialSize = initialSize;
//    }
//
//    public void setKeepAlive(boolean keepAlive) {
//        this.keepAlive = keepAlive;
//    }
//
//    public void setValidationQuery(String validationQuery) {
//        this.validationQuery = validationQuery;
//    }
//
//    public void setTestWhileIdle(boolean testWhileIdle) {
//        this.testWhileIdle = testWhileIdle;
//    }
//
//    public void setTestOnBorrow(boolean testOnBorrow) {
//        this.testOnBorrow = testOnBorrow;
//    }
//
//    public void setMaxWait(int maxWait) {
//        this.maxWait = maxWait;
//    }
//
//    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
//        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
//    }
//
//    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
//        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
//    }
//
//    public void setConnectionInitSqls(List<String> connectionInitSqls) {
//        this.connectionInitSqls = connectionInitSqls;
//    }
//
//    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
//        this.poolPreparedStatements = poolPreparedStatements;
//    }
//
//    public void setTestOnReturn(boolean testOnReturn) {
//        this.testOnReturn = testOnReturn;
//    }
//
//    public void setDriverClassName(String driverClassName) {
//        this.driverClassName = driverClassName;
//    }
//
//    public void setConnectProperties(Properties connectProperties) {
//        this.connectProperties = connectProperties;
//    }
//
//}
