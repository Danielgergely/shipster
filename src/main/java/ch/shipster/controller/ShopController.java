package ch.shipster.controller;

import ch.shipster.data.domain.User;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

// Daniel

@Controller
@RequestMapping(path = "/")
public class ShopController {

    @Autowired
    UserService userService;

    @GetMapping(path = "shop")
    public String getShopView(Model model){
        Optional<User> user = userService.getCurrentUser();
        if ( user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "shop/shop";
    }

    @GetMapping(path = "shop/express")
    public String getExpressView(Model model){
        Optional<User> user = userService.getCurrentUser();
        if ( user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "shop/express";
    }

    @GetMapping(path = "shop/standard")
    public String getStandardView(Model model){
        Optional<User> user = userService.getCurrentUser();
        if ( user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", "no_user");
        }
        return "shop/standard";
    }
}
