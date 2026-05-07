package com.code.security.app.securitywebapp.handlers;

import com.code.security.app.securitywebapp.entities.UserEntity;
import com.code.security.app.securitywebapp.repository.UserRepository;
import com.code.security.app.securitywebapp.services.JWTService;
import com.code.security.app.securitywebapp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2AuthenticationToken token  = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User user = (DefaultOAuth2User) token.getPrincipal();
        String email = user.getAttribute("email").toString();
        UserEntity userEntity = userService.getUserByEmail(email);
        // If UserEntity is null, then register the user.
        if(userEntity == null){
           UserEntity newUser = UserEntity.builder()
                   .username(user.getAttribute("name"))
                   .email(email)
                   .build();
           userEntity = userService.save(newUser);
        }
        String accessToken = jwtService.generateJWTAccessToken(userEntity);
        String refreshToken = jwtService.generateJWTRefreshToken(userEntity);

        Cookie cookie = new Cookie("RefreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // Get Frontend URL
        String frontendURL = "https://localhost:8090/home.html?token="+refreshToken;
        getRedirectStrategy().sendRedirect(request, response, frontendURL);






    }




}
