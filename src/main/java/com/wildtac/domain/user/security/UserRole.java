package com.wildtac.domain.user.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.wildtac.domain.user.security.UserPermission.*;

public enum UserRole {
    USER(Sets.newHashSet(
    )),
    ADMIN(Sets.newHashSet
            (
                    USER_ROLE_WRITE, USER_READ,
                    PRODUCT_WRITE, PRODUCT_REDACT, PRODUCT_DELETE,
                    IMAGE_WRITE,
                    ORDER_REDACT,
                    CATEGORY_WRITE, FEEDBACK_DELETE
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

    @Override
    public String toString() {
        return name();
    }
}
