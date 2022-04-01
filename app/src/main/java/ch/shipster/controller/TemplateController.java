package ch.shipster.controller;

import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class TemplateController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getIndexView( Model model){
        model.addAttribute("userName", "userName");
        return "index.html";
    }

    @GetMapping(path = "team")
    public String getTeamView(Model model){
    //    model.addAttribute("userName", userService.getCurrentUser());
        return "team.html";
    }

    @GetMapping(path = "about")
    public String getAboutView(Model model){
    //    model.addAttribute("userName", userService.getCurrentUser());
        return "about.html";
    }

    @GetMapping(path = "login")
    public String getLoginView(){
        return "user/login.html";
    }

}
