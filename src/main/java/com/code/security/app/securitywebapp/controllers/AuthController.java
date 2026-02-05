package com.code.security.app.securitywebapp.controllers;

import com.code.security.app.securitywebapp.dtos.LoginDTO;
import com.code.security.app.securitywebapp.dtos.LoginResponseDTO;
import com.code.security.app.securitywebapp.dtos.SignUpDTO;
import com.code.security.app.securitywebapp.dtos.UserDTO;
import com.code.security.app.securitywebapp.services.LoginService;
import com.code.security.app.securitywebapp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                                  HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = loginService.login(loginDTO);
        // Creating a cookie and storing this token
        Cookie cookie = new Cookie("AccessToken ", loginResponseDTO.getAccessToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);

    }


}
