package com.yl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@SpringBootApplication
@EnableEurekaClient
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}
}
