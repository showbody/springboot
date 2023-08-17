package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@RestController
@SpringBootApplication
@Slf4j
@MapperScan("com.yc.mappers")
@EnableWebMvc
@EnableScheduling  //开启定时器
@EnableCaching
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);

    }
}
