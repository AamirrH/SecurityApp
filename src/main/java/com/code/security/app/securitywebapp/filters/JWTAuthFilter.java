package com.code.security.app.securitywebapp.filters;

import com.code.security.app.securitywebapp.entities.UserEntity;
import com.code.security.app.securitywebapp.services.JWTService;
import com.code.security.app.securitywebapp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;
    // HandlerExceptionResolver transports the exception in the security context to the dispatcher
    // servlet context.

    // doFilterInternal -> what the filter actually does with the request it receives.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        try {
        // Extracting token from the request, Headers are in key-value pairs and the key for the token is "Authorization"
        final String token  = request.getHeader("Authorization");
        System.out.println(token);
            System.out.println("JWT FILTER HIT");

        // The token is actually present as :- "Bearer fjgunehsfjunsefn8utjhngejht023t2t3.55rsgretertg.53454j"

        // If we don't get any token due to some error or the token does not start with "Bearer .."
        if(token == null || !(token.startsWith("Bearer "))) {
            // pass the request through the filter chain as it is
            filterChain.doFilter(request, response);
            return;
        }

        else {
            // Bearer jdfogj834utu9325fds -> split -> ([""],["jdfogj834utu9325fds"])
            String authToken = token.split("Bearer ")[1];


            // Validating the token and getting the ID from the token
            Long userId = jwtService.getUserIDFromJWTToken(authToken);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Getting the UserEntity
                UserEntity userEntity = userService.getUserByUserId(userId);
                // Creating an authentication token
                UsernamePasswordAuthenticationToken usernamepasswordauthToken
                        = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
                usernamepasswordauthToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                System.out.println("Authorities "+userEntity.getAuthorities());
                // Putting the User into the Spring Security Context Holder
                SecurityContextHolder.getContext().setAuthentication(usernamepasswordauthToken);
                System.out.println("Context Reached");
            }


        }

        }
        catch(Exception e){
            handlerExceptionResolver.resolveException(request,response,null,e);
            return;
        }
        filterChain.doFilter(request, response);
        System.out.println("Filtering Done");



    }
}
