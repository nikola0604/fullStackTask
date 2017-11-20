package com.deploy.fullstacktask.server;

import com.deploy.fullstacktask.server.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServerApplication extends SpringBootServletInitializer
{
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(ServerApplication.class);
	}
	
	@Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource driver = new DriverManagerDataSource();
		
		driver.setDriverClassName("org.postgresql.Driver");
		driver.setUrl(url);
		driver.setUsername(username);
		driver.setPassword(password);

		return driver;
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService)
	{
		return (args) ->
		{
			storageService.deleteAll();
			storageService.init();
		};
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(ServerApplication.class, args);
	}
}
