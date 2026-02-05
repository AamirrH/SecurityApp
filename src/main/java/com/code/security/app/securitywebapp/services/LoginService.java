package com.code.security.app.securitywebapp.services;

import com.code.security.app.securitywebapp.dtos.LoginDTO;
import com.code.security.app.securitywebapp.dtos.LoginResponseDTO;
import com.code.security.app.securitywebapp.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    // Updated Login Method
    public LoginResponseDTO login(LoginDTO loginDTO){
        System.out.println("Test - 1");
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        // Used to get the user-entity which has been authenticated
        UserEntity userEntity = (UserEntity) auth.getPrincipal();
        String accessToken = jwtService.generateJWTAccessToken(userEntity);
        String refreshToken = jwtService.generateJWTRefreshToken(userEntity);
        return new LoginResponseDTO(userEntity.getId(), accessToken, refreshToken);
    }

}
