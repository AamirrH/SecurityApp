package com.code.security.app.securitywebapp.controllers;


import com.code.security.app.securitywebapp.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/home")
    public String testAPI(){
//        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(user);
        return "Welcome to the Home Page";
    }


}
