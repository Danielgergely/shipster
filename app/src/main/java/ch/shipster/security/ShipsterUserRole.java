package ch.shipster.security;

// Daniel
// amigoscode - Spring Boot Security

import com.google.common.collect.Sets;

import java.util.Set;

import static ch.shipster.security.ShipsterUserPermission.*;

public enum ShipsterUserRole {
    USER(Sets.newHashSet(USER_READ)),
    DEVELOPER(Sets.newHashSet(USER_READ, USER_WRITE)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, USER_DELETE));

    private final Set<ShipsterUserPermission> permissions;

    ShipsterUserRole(Set<ShipsterUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ShipsterUserPermission> getPermissions() {
        return permissions;
    }
}
