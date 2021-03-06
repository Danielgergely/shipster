package ch.shipster.security;

// Daniel
// amigoscode - Spring Boot Security
public enum ShipsterUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete");

    private final String permission;

    ShipsterUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
