package ch.shipster.controller;

import ch.shipster.data.domain.User;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("admin")
    public String getAdminView(Model model){
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            model.addAttribute("user", user.get());
            return "admin/admin";
        }
    }
}
