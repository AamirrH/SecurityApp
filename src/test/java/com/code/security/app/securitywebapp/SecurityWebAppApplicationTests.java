package com.code.security.app.securitywebapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class SecurityWebAppApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(HttpStatus.UNAUTHORIZED.value());
    }

}
