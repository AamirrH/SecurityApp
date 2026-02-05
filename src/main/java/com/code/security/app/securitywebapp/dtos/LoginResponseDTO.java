package com.code.security.app.securitywebapp.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {

    private Long id;
    private String accessToken;
    private String refreshToken;

}
