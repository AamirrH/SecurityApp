package com.code.security.app.securitywebapp.controllers;


import com.code.security.app.securitywebapp.dtos.TestDTO;
import com.code.security.app.securitywebapp.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SecurityApp")
public class SecurityController {

    @Secured("ROLE_SECURITY_ADMIN")
    @GetMapping("/home")
    public ResponseEntity<TestDTO> testAPI(){
        TestDTO testDTO = new TestDTO();
        testDTO.setTestMessage("You are an Admin");
        return ResponseEntity.ok(testDTO);
    }


}
