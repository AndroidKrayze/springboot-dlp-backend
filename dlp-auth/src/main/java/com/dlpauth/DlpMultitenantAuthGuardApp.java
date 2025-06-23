package com.dlpauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.dlpauth")
@SpringBootApplication
public class DlpMultitenantAuthGuardApp {
    public static void main(String[] args) {
        SpringApplication.run(DlpMultitenantAuthGuardApp.class, args);
    }
}



