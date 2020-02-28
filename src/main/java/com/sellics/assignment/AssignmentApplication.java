package com.sellics.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

	/**
	 * RestTemplate bean to be used within application.
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(getClientHttpRequestFactory());
	}

	//Override timeouts in request factory
	private SimpleClientHttpRequestFactory getClientHttpRequestFactory()
	{
		SimpleClientHttpRequestFactory clientHttpRequestFactory
				= new SimpleClientHttpRequestFactory();
		//Connect timeout
		clientHttpRequestFactory.setConnectTimeout(10000);

		//Read timeout
		clientHttpRequestFactory.setReadTimeout(10000);
		return clientHttpRequestFactory;
	}
}
