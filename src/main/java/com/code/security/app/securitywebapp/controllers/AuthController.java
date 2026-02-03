package com.code.security.app.securitywebapp.controllers;

import com.code.security.app.securitywebapp.dtos.LoginDTO;
import com.code.security.app.securitywebapp.dtos.SignUpDTO;
import com.code.security.app.securitywebapp.dtos.UserDTO;
import com.code.security.app.securitywebapp.services.LoginService;
import com.code.security.app.securitywebapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SecurityApp")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final LoginService loginService;



    // PostMapping because we are going to send data/credentials
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(userService.signup(signUpDTO));
    }

    // PostMapping because we are going to send data/credentials
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(loginService.login(loginDTO));
    }


}
