package ch.shipster.controller;

import ch.shipster.data.domain.User;
import ch.shipster.data.repository.UserRepository;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/security/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
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
