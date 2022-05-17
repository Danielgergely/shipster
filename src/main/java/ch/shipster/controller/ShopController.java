package ch.shipster.controller;

import ch.shipster.data.domain.*;
import ch.shipster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NegativeOrZero;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CheckoutService checkoutService;

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
    public String getStandardArticleView(@RequestParam Long articleId, Model model) throws IOException, InterruptedException {
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


    @GetMapping(path = "shop/express/article")
    public String getExpressArticleView(@RequestParam Long articleId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Article product = articleService.findById(articleId);
            Address address = addressService.findAddressById(user.get().getAddressId());
            int distance = DistanceCalculator.calculateDistance(address);
            int paletSpace = (int) Math.ceil(product.getPalletSpace());
            Cost cost = costService.getMostExpensiveCost(distance, paletSpace);
            model.addAttribute("distance", distance);
            model.addAttribute("price", cost.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("user", user.get());
        }
        return "shop/article";
    }

    @PutMapping(path = "shop/article/order")
    public void createOrder(@RequestParam Long userId, @RequestParam Long articleId, Model model) throws Exception {
        User user = userService.findById(userId);
        Order order = orderService.getBasketByUser(user);
        OrderItem orderItem = orderItemService.getOrderItem(articleId, order.getId());
        orderItemService.add(articleId, order.getId());
    }


    @GetMapping(path = "shop/basket")
    public String getBasket(Model model) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if(user.isEmpty()){
            return "user/login";
        } else {
            Order order = orderService.getBasketByUser(user.get());
            List<OrderItem> orderItems = orderService.getOrderItems(order);
            List<Article> articles = new ArrayList<>();
            for (OrderItem oI : orderItems) {
                Article a = orderItemService.getArticle(oI.getArticleId());
                articles.add(a);
            }
            model.addAttribute("order", order);
            model.addAttribute("articles", articles);
            model.addAttribute("user", user.get());
            return "shop/basket";
        }
    }

    @GetMapping(path = "shop/standard/article")
    public String getStandardArticleView(@RequestParam Long articleId, Model model) throws IOException, InterruptedException {
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


    @GetMapping(path = "shop/express/article")
    public String getExpressArticleView(@RequestParam Long articleId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Article product = articleService.findById(articleId);
            Address address = addressService.findAddressById(user.get().getAddressId());
            int distance = DistanceCalculator.calculateDistance(address);
            int paletSpace = (int) Math.ceil(product.getPalletSpace());
            Cost cost = costService.getMostExpensiveCost(distance, paletSpace);
            model.addAttribute("distance", distance);
            model.addAttribute("price", cost.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("user", user.get());
        }
        return "shop/article";
    }

    @PutMapping(path = "shop/article/order")
    public void addItemToBasket(@RequestParam Long userId, @RequestParam Long articleId, Model model) throws Exception {
        User user = userService.findById(userId);
        Order order = orderService.getBasketByUser(user);
        orderItemService.add(articleId, order.getId());
    }


    @GetMapping(path = "shop/basket")
    public String getBasket(Model model) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if(user.isEmpty()){
            return "user/login";
        } else {
            Order order = orderService.getBasketByUser(user.get());
            List<Article> articles = orderService.getArticlesInBasket(user.get().getUserId());
            List<OrderItem> orderItems = orderService.getOrderItems(order);
            model.addAttribute("order", order);
            model.addAttribute("articles", articles);
            model.addAttribute("user", user.get());
            return "shop/basket";
        }
    }
}
