package com.ian.homework.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 如果不希望数据源在启动配置时就加载好，可以定制这个方法，从任何你希望的地方读取并返回数据源，
     * 比如从数据库，文件，外部接口等读取数据源信息，并最终返回一个DataSource实现类对象即可
     * @return
     */
    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    /**
     * 如果希望所有的数据源在启动配置时加载好，这里通过设置数据源key值来切换数据源，定制这个方法
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    /**
     * 设置默认的数据源
     * @param defaultDataSource
     */
    public void setDefaultDataSource(Object defaultDataSource){
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    /**
     * 社渚数据源
     * @param dataSource
     */
    public void setDataSource(Map<Object,Object> dataSource){
        super.setTargetDataSources(dataSource);
        //将数据源的key放到数据源上下文的key集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.addDataSourceKey(dataSource.keySet());
    }
}