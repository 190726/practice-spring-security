package com.sk;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    /*
     * Spring boot 초기화
     */
    @Bean
    public ApplicationRunner myRunner() {
        return args -> {
            //controller.home();
        };
    }
}
