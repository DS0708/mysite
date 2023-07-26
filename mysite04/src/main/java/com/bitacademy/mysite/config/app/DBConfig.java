package com.bitacademy.mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/bitacademy/mysite/config/app/jdbc.properties")
public class DBConfig {
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.userName=webdb"));
		dataSource.setPassword(env.getProperty("jdbc.password=webdb"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialsize",Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive",Integer.class));
		
		return dataSource;
	}
}