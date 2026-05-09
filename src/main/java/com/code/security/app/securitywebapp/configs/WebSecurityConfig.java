package com.code.security.app.securitywebapp.configs;

import com.code.security.app.securitywebapp.entities.enums.Roles;
import com.code.security.app.securitywebapp.filters.JWTAuthFilter;
import com.code.security.app.securitywebapp.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.code.security.app.securitywebapp.entities.enums.Roles.SECURITY_ADMIN;

@Configuration
@EnableWebSecurity // Helps us to customize the filter chain
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthFilter authFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String [] routes = {"/SecurityApp/login","/SecurityApp/signup","/home.html/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Through this every request will get authenticated.
                .authorizeHttpRequests(auth -> auth
                        // Permit certain routes without authentication for all users.
                        .requestMatchers(routes).permitAll()
                        // Now only users with the Admin role will be able to access the test route
                        .requestMatchers(HttpMethod.GET,"/SecurityApp/test").hasRole(SECURITY_ADMIN.name())
                        // Permit certain endpoints for users with a specific role without auth
                        .requestMatchers("/More").hasAnyRole("USER","ADMIN")
                        .anyRequest()
                        .authenticated())
                // Disable CSRF
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                // Disable Sessions and enable Stateless authentication (Session ID
                // no longer stored in inMemDB
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Adds the authfilter before UPAFilter
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2LoginConfig ->oauth2LoginConfig
                        // fallsback to failureURL if auth is incorrect.
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );
//                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }










}
