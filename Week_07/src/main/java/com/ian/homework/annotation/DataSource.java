package com.ian.homework.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE,ElementType.PARAMETER})//设置注解的作用范围
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String value() default "master";
}
