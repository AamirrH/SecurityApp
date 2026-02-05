package com.code.security.app.securitywebapp.configs;

import com.code.security.app.securitywebapp.filters.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Helps us to customise the filter chain
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Through this every request will get authenticated.
                .authorizeHttpRequests(auth -> auth
                        // Permit certain routes without authentication for all users.
                        .requestMatchers("/SecurityApp/login","/SecurityApp/signup")
                        .permitAll()
                        // Permit certain endpoints for users with a specific role without auth
                        .requestMatchers("/More").hasAnyRole("USER","ADMIN")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                // Disable CSRF
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                // Disable Sessions and enable Stateless authentication (Session ID
                // no longer stored in inMemDB
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Adds the authfilter before UPAFilter
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);


//                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
