package com.lhrlyn.cn.lhrlynadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.lhrlyn.cn.lhrlynadmin.user.mapper")
@SpringBootApplication
public class LhrlynAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LhrlynAdminApplication.class, args);
    }

}
