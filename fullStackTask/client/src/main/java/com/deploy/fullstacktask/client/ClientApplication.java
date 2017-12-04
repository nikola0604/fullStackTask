package com.deploy.fullstacktask.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClientApplication
{
	@Bean
	protected ModelMap modelMap()
	{
		return new ModelMap();
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(ClientApplication.class, args);
	}
}
