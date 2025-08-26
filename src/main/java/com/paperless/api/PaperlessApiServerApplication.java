package com.paperless.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaperlessApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperlessApiServerApplication.class, args);
    }

}
