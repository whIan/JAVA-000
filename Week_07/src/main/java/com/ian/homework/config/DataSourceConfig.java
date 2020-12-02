package com.ian.homework.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.ian.homework.initdata.dao.slave",sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class DataSourceConfig {
    @Value("${slave.datasource.url}")
    private String url;
    @Value("${slave.datasource.username}")
    private String username;
    @Value("${slave.datasource.password}")
    private String password;
    /**本数据源扫描的mapper路径*/
    static final String MAPPER_LOCATION = "classpath:mapper/slave/*.xml";


    /**创建数据源*/
    @Bean(name = "slaveDataSource")
    @Primary
    public DataSource getFirstDataSource() {
        DataSource build =  DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
        return build;
    }


    /**创建SessionFactory*/
    @Bean(name = "slaveSqlSessionFactory")
    @Primary
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置mapper配置文件
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    /**创建事务管理器*/
    @Bean("slaveTransactionManger")
    @Primary
    public DataSourceTransactionManager slaveTransactionManger(@Qualifier("slaveDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**创建SqlSessionTemplate*/
    @Bean(name = "slaveSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
