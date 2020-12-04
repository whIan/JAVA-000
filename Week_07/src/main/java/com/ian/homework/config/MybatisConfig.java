package com.ian.homework.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.ian.homework.goods.dao")//扫瞄DAO的包
@Slf4j
public class MybatisConfig {

    /**
     * 创建主数据源
     * @return
     */
    @Bean("master")
    @Primary //设置优先级
    @ConfigurationProperties("spring.datasource.master") //配置属性文件
    public DataSource master(){
        return DataSourceBuilder.create().build();//builder建造者模式
    }

    /**
     * 创建从数据源
     * @return
     */
    @Bean("slave")
    @Primary
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slave(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 生成自定义的数据源
     * @return
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> mapDataSource = new HashMap<Object,Object>(2);
        mapDataSource.put("master",master());
        mapDataSource.put("slave",slave());
        //将master数据源作为指定的数据源
        dynamicDataSource.setDefaultDataSource(master());
        //将master和slave数据源作为指定的数据源
        dynamicDataSource.setDataSource(mapDataSource);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        //配置数据源，此处配置为关键配置，如果没有将dynamicDataSource作为数据源则不能实现切换
        sessionFactoryBean.setDataSource(dynamicDataSource());
        //扫描model-entity的包
        sessionFactoryBean.setTypeAliasesPackage("com.ian.homework.goods.model");
        //扫描映射文件
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mapper/*.xml"));
        return sessionFactoryBean.getObject();
    }

    /**
     * 配置事务管理，使用事务时哎方法头部添加@Transactional注解即可
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}
