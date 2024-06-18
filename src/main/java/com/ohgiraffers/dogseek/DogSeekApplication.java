package com.ohgiraffers.dogseek;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ohgiraffers.dogseek", annotationClass = Mapper.class)
public class DogSeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogSeekApplication.class, args);
    }

}
