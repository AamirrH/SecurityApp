package com.code.security.app.securitywebapp.utils;

import com.code.security.app.securitywebapp.entities.enums.Permissions;
import com.code.security.app.securitywebapp.entities.enums.Roles;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.code.security.app.securitywebapp.entities.enums.Permissions.*;
import static com.code.security.app.securitywebapp.entities.enums.Roles.SECURITY_ADMIN;
import static com.code.security.app.securitywebapp.entities.enums.Roles.SECURITY_USER;

@UtilityClass
public class PermissionMapping {

    public static final Map<Roles, Set<Permissions>> rolePermissionMap = Map.of(
            SECURITY_USER, Set.of(POST_CREATE, POST_VIEW),
            SECURITY_ADMIN, Set.of(POST_DELETE, POST_UPDATE, USER_CREATE, USER_DELETE, USER_UPDATE, USER_VIEW)
    );


    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Roles roles) {
        return rolePermissionMap.get(roles).stream()
                .map(permissions -> new SimpleGrantedAuthority((permissions.name())))
                .collect(Collectors.toSet());


    }
}
