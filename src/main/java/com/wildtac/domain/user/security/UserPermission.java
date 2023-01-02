package com.wildtac.domain.user.security;

public enum UserPermission {
    PRODUCT_WRITE("product:write"), PRODUCT_DELETE("product:delete"), PRODUCT_REDACT("product:redact"),
    CATEGORY_WRITE("category:write"), CATEGORY_DELETE("category:delete"), CATEGORY_REDACT("category:redact"),
    FEEDBACK_DELETE("feedback:delete"),
    IMAGE_WRITE("image:write"),
    USER_READ("user:read"), USER_ROLE_WRITE("user_role:write"),
    ORDER_REDACT("order:redact");


    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "UserPermission{" +
                "permission='" + permission + '\'' +
                '}';
    }
}
