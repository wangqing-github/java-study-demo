package wq.study.demo.config;//package com.example.demo.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import com.example.demo.config.generalConfig.GeneralDataBasePropertiesConfig;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
//import javax.sql.DataSource;
//
////@Configuration
////@MapperScan(basePackages = "com.example.demo.mapper", sqlSessionFactoryRef = "serverSqlSessionFactory")
//public class ServerDataSourceConfig {
//
//    @Autowired
//    private GeneralDataBasePropertiesConfig generalDataBasePropertiesConfig;
//
//    @Bean(name = "serverDataSource",initMethod = "init",destroyMethod = "close")
//    @ConfigurationProperties(prefix = "spring.datasource.server")
//    @Primary
//    public DruidDataSource testDataSource() {
//        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
//        generalDataBasePropertiesConfig.buildDatasource(druidDataSource);
//        return druidDataSource;
//    }
//
//    @Bean(name = "serverSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory testSqlSessionFactory(@Qualifier("serverDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:com/example/demo/mapper/**/*.xml");
//        bean.setMapperLocations(resources);
//        return bean.getObject();
//    }
//
//    @Bean(name = "serverTransactionManager")
//    @Primary
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("serverDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "serverTransactionInterceptor")
//    @Primary
//    public TransactionInterceptor TxAdvice(@Qualifier("serverTransactionManager") DataSourceTransactionManager transactionManager) {
//        NameMatchTransactionAttributeSource source = generalDataBasePropertiesConfig.getPropagationSource();
//        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//        transactionInterceptor.setTransactionManager(transactionManager);
//        transactionInterceptor.setTransactionAttributeSource(source);
//        return transactionInterceptor;
//    }
//
//    @Bean(name = "serverTxAdviceAdvisor")
//    @Primary
//    public Advisor txAdviceAdvisor(@Qualifier("serverTransactionInterceptor") TransactionInterceptor transactionInterceptor) {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution (* com.example.demo.service..*(..))");
//        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
//    }
//}
