package com.gmail.pashasimonpure.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.gmail.pashasimonpure.web",
        "com.gmail.pashasimonpure.service",
        "com.gmail.pashasimonpure.repository"},
        exclude = UserDetailsServiceAutoConfiguration.class)
public class WebModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }

}