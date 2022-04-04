package ch.shipster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/shop")
public class ShopController {

    @GetMapping
    public String getIndexView(){
        return "shop.html";
    }
}