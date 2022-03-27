package ch.shipster.controller;

import ch.shipster.data.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/users")
public class UserManagementController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "Daniel Gergely"),
            new User(2, "Jonas Mägli"),
            new User(3, "Timo Grünenfelder"),
            new User(4, "Manuel Oliva"),
            new User(5, "Giacomo Travaglione")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_DEVELOPER')")
    public List<User> getAllUsers() {
        return USERS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void registerNewUser(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUser(@PathVariable("id") Integer id) {
        System.out.println(id);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        System.out.println(String.format("%s %s", id, user));
    }

}
