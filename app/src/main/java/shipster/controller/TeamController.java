package shipster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/team")
public class TeamController {

    @GetMapping
    public String getIndexView(){
        return "team.html";
    }
}
