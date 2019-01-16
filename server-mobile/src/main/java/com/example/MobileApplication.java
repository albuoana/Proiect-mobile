package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@ComponentScan(basePackages = {
        "utils",
        "controllers",
        "services",
        "model"
})

@EnableWebMvc
@EnableJpaRepositories(basePackages = {"repositories"})
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class MobileApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileApplication.class, args);
    }
}
