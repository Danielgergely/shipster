package shipster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    @GetMapping
    public String getIndexView(){
        return "index.html";
    }

    @GetMapping(path = "/team")
    public String getTeamView(){
        return "team.html";
    }

    @GetMapping(path = "/about")
    public String getAboutView(){
        return "about.html";
    }

}
