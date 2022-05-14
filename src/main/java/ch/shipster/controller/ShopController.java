package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.Cost;
import ch.shipster.data.domain.User;
import ch.shipster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


// Daniel

@Controller
@RequestMapping(path = "/")
public class ShopController {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    AddressService addressService;

    @Autowired
    CostService costService;

    @GetMapping(path = "shop")
    public String getShopView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            model.addAttribute("user", "no_user");
        } else {
            model.addAttribute("user", user.get());
        }
        return "shop/shop";
    }

    @GetMapping(path = "shop/express")
    public String getExpressView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            model.addAttribute("user", "no_user");
        } else {
            List<Article> products = articleService.getAllArticles();
            model.addAttribute("products", products);
            model.addAttribute("user", user.get());
        }
        return "shop/express";
    }

    @GetMapping(path = "shop/standard")
    public String getStandardView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            model.addAttribute("user", "no_user");
        } else {
            List<Article> products = articleService.getAllArticles();
            model.addAttribute("products", products);
            model.addAttribute("user", user.get());
        }
        return "shop/standard";
    }

    @GetMapping(path = "shop/standard/article")
    public String getStandardArticleView(@RequestParam Long articleId, Model model) throws JSONException, IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Article product = articleService.findById(articleId);
            Address address = addressService.findAddressById(user.get().getAddressId());
            int distance = DistanceCalculator.calculateDistance(address);
            int paletSpace = (int) Math.ceil(product.getPalletSpace());
            Cost cost = costService.getCheapestCost(distance, paletSpace);
            model.addAttribute("distance", distance);
            model.addAttribute("price", cost.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("user", user.get());
        }
        return "shop/article";
    }
}
