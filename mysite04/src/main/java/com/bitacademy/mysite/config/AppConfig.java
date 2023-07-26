package com.bitacademy.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.bitacademy.mysite.config.app.DBConfig;
import com.bitacademy.mysite.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.bitacademy.mysite.service", "com.bitacademy.mysite.repository", "com.bitacademy.mysite.exception", "com.bitacademy.mysite.aop"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {	
}