package com.code.security.app.securitywebapp.dtos;

import com.code.security.app.securitywebapp.entities.enums.Roles;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/*
DTO through which we signup to a platform, providing only necessary credentials
 */
@Data
@Getter
@Setter
public class SignUpDTO {

    private String username;
    @Email
    private String email;

    private String password;

    private Set<Roles> roles;


}
