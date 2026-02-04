package com.code.security.app.securitywebapp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class UserDTO {

    private Long id;
    private String username;
    private String email;

}
