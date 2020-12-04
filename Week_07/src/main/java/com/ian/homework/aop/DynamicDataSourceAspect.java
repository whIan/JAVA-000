package com.ian.homework.aop;

import com.ian.homework.annotation.DataSource;
import com.ian.homework.config.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1) //优先于事务注解执行
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint point, DataSource dataSource){
        if(!DynamicDataSourceContextHolder.containDataSourceKey(dataSource.value())){
            System.out.println("数据库不匹配 : "+ dataSource.value());
        }else{
            System.out.println("数据库切换 : "+ dataSource.value());
            DynamicDataSourceContextHolder.setDataSourceKey(dataSource.value());
        }
    }

    /**
     *  清空数据源
     * @param point
     * @param dataSource
     */
    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point,DataSource dataSource){
        DynamicDataSourceContextHolder.cleanDataSource();
        System.out.println("数据库连接清空："+dataSource.value());
    }
}

