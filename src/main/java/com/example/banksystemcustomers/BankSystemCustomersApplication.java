package com.example.banksystemcustomers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.banksystemcustomers.mapper")
public class BankSystemCustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankSystemCustomersApplication.class, args);
    }

}
