package com.example.onlineeducationplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.onlineeducationplatform.mapper")
public class OnlineEducationPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineEducationPlatformApplication.class, args);
    }
}
