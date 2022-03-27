package ch.shipster.controller;

import ch.shipster.data.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "Daniel Gergely"),
            new User(2, "Jonas Mägli"),
            new User(3, "Timo Grünenfelder"),
            new User(4, "Manuel Oliva"),
            new User(5, "Giacomo Travaglione")
    );

    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return USERS.stream()
                .filter(user -> userId.equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "User " + userId + " does not exist."
                ));
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "user/login.html";
    }

    @GetMapping("/register")
    public String getRegisterView() {
        return "user/register.html";
    }

    @GetMapping("/profile/edit")
    public String getEditProfileView() {
        return "user/profileEdit.html";
    }
}
