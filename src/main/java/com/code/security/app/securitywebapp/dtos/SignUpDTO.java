package com.code.security.app.securitywebapp.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

}
