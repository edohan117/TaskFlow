package com.edohan.innoNest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.edohan.innoNest.mapper")
public class InnoNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnoNestApplication.class, args);
	}

}
