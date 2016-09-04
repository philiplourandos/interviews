package com.noxrentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author plourand
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class NoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoxApplication.class, args);
    }
}
