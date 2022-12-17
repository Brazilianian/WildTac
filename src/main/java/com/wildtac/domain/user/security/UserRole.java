package com.wildtac.domain.user.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.wildtac.domain.user.security.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(
            PRODUCT_READ,
            CATEGORY_READ,
            FEEDBACK_READ, FEEDBACK_WRITE, FEEDBACK_LIKE,
            IMAGE_READ
    )),
    ADMIN(Sets.newHashSet
            (PRODUCT_READ, PRODUCT_WRITE, PRODUCT_DELETE, PRODUCT_REDACT,
                    CATEGORY_READ, CATEGORY_WRITE, CATEGORY_DELETE, CATEGORY_REDACT,
                    FEEDBACK_READ, FEEDBACK_WRITE, FEEDBACK_LIKE,
                    IMAGE_READ, IMAGE_WRITE
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return permissions;
    }
}
