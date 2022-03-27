package ch.shipster.security;

// Daniel
// amigoscode - Spring Boot Security

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
