package com.code.security.app.securitywebapp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String email;

}
