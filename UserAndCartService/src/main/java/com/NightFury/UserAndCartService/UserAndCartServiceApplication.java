package com.NightFury.UserAndCartService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserAndCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAndCartServiceApplication.class, args);
	}

}
