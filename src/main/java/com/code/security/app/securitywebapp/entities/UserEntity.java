package com.code.security.app.securitywebapp.entities;

import com.code.security.app.securitywebapp.entities.enums.Permissions;
import com.code.security.app.securitywebapp.entities.enums.Roles;
import com.code.security.app.securitywebapp.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
/*
Actual class that gets mapped to PostgreSQL database containing username,
email, password, Roles and id
 */


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // @Enumerated -> how do you want to store this in DB? using ordinals or the String names
    private Set<Roles> roles; // List of Roles

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // @Enumerated -> how do you want to store this in DB? using ordinals or the String names
    private Set<Permissions> permissions;


    @Override
    // What kind of Authority does the user have? What actions can they perform?
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> set = PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(set);
                }
        );
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
