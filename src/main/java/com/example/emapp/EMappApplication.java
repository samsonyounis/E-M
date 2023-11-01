package com.example.emapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EMappApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMappApplication.class, args);
    }

}
