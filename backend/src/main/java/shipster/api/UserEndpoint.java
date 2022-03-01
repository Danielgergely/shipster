package shipster.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import shipster.data.domain.User;
import shipster.exceptions.NoPermissionException;
import shipster.exceptions.NotFoundException;
import shipster.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserEndpoint {

    @Autowired UserService userService;

    @PreAuthorize("hasRole('WRITE_PRIVILEGE')")
    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<List<User>> getAll() {
        List<User> response = userService.findAllUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            path = "/user/register",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<User> postUser(@RequestBody User user) {
        try {
            user = userService.createUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        // https://www.baeldung.com/spring-uricomponentsbuilder
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user;
        try {
            user = userService.getUser(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "userId") Long userId) {
        try {
            User user = userService.getUser(userId);
            userService.deleteUser(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "users/currennt", produces = "application/json")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping(path = "signup_check/username", produces = "application/json")
    public boolean usernameAvailable(@RequestParam String username) {
        return userService.usernameAvailable(username);
    }

    @PutMapping(path = "/users/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> editUser(@PathVariable(value = "id") Long id, @RequestParam User user) {
        try {
            user = userService.editUser(id, user);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Void> init() {
        return ResponseEntity.ok().build();
    }
}
