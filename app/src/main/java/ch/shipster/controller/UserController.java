package ch.shipster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

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
