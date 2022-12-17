package com.wildtac.domain.user.security;

public enum UserPermission {
    PRODUCT_READ("product:read"), PRODUCT_WRITE("product:write"), PRODUCT_DELETE("product:delete"), PRODUCT_REDACT("product:redact"),
    CATEGORY_READ("category:read"), CATEGORY_WRITE("category:write"), CATEGORY_DELETE("category:delete"), CATEGORY_REDACT("category:redact"),
    FEEDBACK_READ("feedback:read"), FEEDBACK_WRITE("feedback:write"), FEEDBACK_LIKE("feedback:like"),
    IMAGE_READ("image:read"), IMAGE_WRITE("image:write");
    

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
