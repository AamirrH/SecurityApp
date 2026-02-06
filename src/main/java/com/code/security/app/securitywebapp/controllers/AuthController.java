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
        System.out.println("Checkpoint - 2");
        // Creating a cookie and storing this token
        // Cookie name cant contain white spaces -> empty response in Postman
        Cookie cookie = new Cookie("RefreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh (HttpServletRequest request) {
        String RefreshToken = new String();
        Cookie[] cookies = request.getCookies();
        for(int index = 0; index < cookies.length; index++) {
            if(cookies[index].getName().equals("RefreshToken")) {
                RefreshToken = cookies[index].getValue();
                break;
            }
        }
        LoginResponseDTO loginResponseDTO = loginService.refreshToken(RefreshToken);
        return ResponseEntity.ok(loginResponseDTO);


    }



}
