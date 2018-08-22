package com.xiejieyi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoApplication 命名以 微服务名+Application
 * @EnableEurekaClient 注册到eureka上
 */
@SpringBootApplication
// @EnableEurekaClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

