package com.bootlabs.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SpringBoot3RedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3RedisCacheApplication.class, args);
	}

}
