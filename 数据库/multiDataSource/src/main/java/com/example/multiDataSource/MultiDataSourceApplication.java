package com.example.multiDataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.multiDataSource.mapper")
public class MultiDataSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourceApplication.class, args);
	}

}
