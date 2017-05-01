package com.alexboriskin.university.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**/

public class ApplicationContextProvider {
    private static ApplicationContext applicationContext;

    private ApplicationContextProvider() {
        
    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath*:Spring-Module.xml");
        }
        return applicationContext;
    }

}