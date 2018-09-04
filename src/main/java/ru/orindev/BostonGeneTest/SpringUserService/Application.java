package ru.orindev.BostonGeneTest.SpringUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@SpringBootApplication
@EnableJpaRepositories("ru.orindev.BostonGeneTest.SpringUserService.repository")
@ComponentScan(basePackages = { "ru.orindev.BostonGeneTest.SpringUserService.controller" ,
                                "ru.orindev.BostonGeneTest.SpringUserService.config"})
@EntityScan("ru.orindev.BostonGeneTest.SpringUserService.model")

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
