package com.ian.week5.homework.beanload;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanLoad {

    BeanLoad() {
        System.out.println("BeanLoad Construct.");
    }

    @Bean
    public MyBean1 myBean1() {
        return new MyBean1();
    }

    public class MyBean1 {
        MyBean1() {
            System.out.println("MyBean1 Construct.");
        }
    }

    /**
     * xml
     */
    public class MyBean2 {
        MyBean2() {
            System.out.println("MyBean2 Construct.");
        }
    }



}
