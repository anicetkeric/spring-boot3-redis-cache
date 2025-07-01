package com.bootlabs.caching;

import org.springframework.boot.SpringApplication;

public class TestSpringBoot3RedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringBoot3RedisCacheApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
