package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

// Daniel

@Controller
@RequestMapping(path = "/")
public class TemplateController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getIndexView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "index";
    }

    @GetMapping(path = "team")
    public String getTeamView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "team";
    }

    @GetMapping(path = "about")
    public String getAboutView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "about";
    }

    @GetMapping(path = "login")
    public String getLoginView() {
        return "user/login";
    }

    @GetMapping(path = "register")
    public String getRegisterView(@RequestParam(name = "message", required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("user", new User());
        model.addAttribute("address", new Address());
        return "user/register";
    }
}
