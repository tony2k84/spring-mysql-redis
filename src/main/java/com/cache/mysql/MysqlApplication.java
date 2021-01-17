package com.cache.mysql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.Jedis;

@SpringBootApplication
public class MysqlApplication {
	
	@Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

	public static void main(String[] args) {
		SpringApplication.run(MysqlApplication.class, args);
	}
	
	@Bean
	public Jedis jedis() {
		Jedis jedis = new Jedis(redisHost, redisPort);
		return jedis;
	}

}
