package com.aias.demo.framework.config;

import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void configure(SpringProcessEngineConfiguration configuration) {
        // 设置数据源
        configuration.setDataSource(dataSource);
        // 自动创建数据库表
        configuration
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 开启异步执行器
        configuration.setAsyncExecutorActivate(false);
        // 设置事务管理器
        configuration.setTransactionManager(transactionManager);
        // 设置活动名称字体
        configuration.setActivityFontName("宋体");
        // 设置标签字体
        configuration.setLabelFontName("宋体");
        // 设置注释字体
        configuration.setAnnotationFontName("宋体");
    }
}
