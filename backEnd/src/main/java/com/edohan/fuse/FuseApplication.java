package com.edohan.fuse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.edohan.fuse.mapper")
public class FuseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuseApplication.class, args);
	}

}