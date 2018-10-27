package com.anyun100.storage.monitor.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
    
    @RequestMapping("/logout-success")
    public String logout(Principal user) {
        return "error_code: 0";
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
