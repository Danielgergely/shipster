package ch.shipster.controller;

import ch.shipster.data.domain.*;
import ch.shipster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    ProviderService providerService;

    @Autowired
    ShippingCostCalculator shippingCostCalculator;

    @GetMapping(path = "shop")
    public String getShopView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            model.addAttribute("user", "no_user");
        } else {
            List<Article> products = articleService.getAllArticles();
            model.addAttribute("products", products);
            model.addAttribute("user", user.get());
        }
        return "shop/shop";
    }

    @PostMapping(path = "shop/basket/add")
    @ResponseBody
    public String addToBasket(@RequestParam Long articleId) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "{\"user not logged in\":1}";
        } else {
            Order order = orderService.getBasketByUser(user.get());
            orderItemService.add(articleId, order.getId());
            return "{\"message\":\"Product added to basket\"}";
        }
    }

    @GetMapping(path = "shop/article")
    public String getArticleView(@RequestParam Long articleId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Article product = articleService.findById(articleId);
            Address address = addressService.findAddressById(user.get().getAddressId());
            int distance = DistanceCalculator.calculateDistance(address);
            int paletSpace = (int) Math.ceil(product.getPalletSpace());
            Cost cost = costService.getCheapestCost(distance, paletSpace);
            Provider provider = providerService.getProviderById(cost.getProviderId());
            model.addAttribute("distance", distance);
            model.addAttribute("price", cost.getPrice());
            model.addAttribute("product", product);
            model.addAttribute("user", user.get());
            model.addAttribute("provider_id", provider.getId());
        }
        return "shop/article";
    }

    @GetMapping(path = "shop/article/add")
    public String addArticle(@RequestParam Long articleId, @RequestParam Long orderId, @RequestParam(name = "provider_id", required = false) Long providerId, RedirectAttributes redirectAttributes) throws Exception {
        orderItemService.add(articleId, orderId);
        redirectAttributes.addAttribute("message", "Product added to basket");
        redirectAttributes.addAttribute("provider_id", providerId);
        return "redirect:/shop/basket";
    }

    @GetMapping(path = "shop/article/remove")
    public String removeArticle(@RequestParam Long articleId, @RequestParam Long orderId, @RequestParam(name = "provider_id", required = false) Long providerId, RedirectAttributes redirectAttributes) throws Exception {
        orderItemService.remove(articleId, orderId);
        redirectAttributes.addAttribute("message", "Product removed from basket");
        redirectAttributes.addAttribute("provider_id", providerId);
        return "redirect:/shop/basket";
    }


    @GetMapping(path = "shop/basket")
    public String getBasket(@RequestParam(name = "provider_id", required = false) Long providerId, Model model) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            if (providerId == null) {
                providerId = 2L;
            }
            Order order = orderService.getBasketByUser(user.get());
            List<Article> articles = orderService.getArticlesInBasket(user.get().getUserId());
            List<OrderItem> orderItems = orderService.getOrderItems(order);
            Float articlesTotalPrice = checkoutService.calculateTotalOrderPrice(order);
            Float totalPrice = checkoutService.calculateTotalOrderPriceWithShipping(order, providerId);
            Provider provider = providerService.getProviderById(providerId);
            float palletSpace = shippingCostCalculator.palletCalculation(order.getId());
            model.addAttribute("order", order);
            model.addAttribute("articles", articles);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("articlesTotalPrice", articlesTotalPrice);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("provider", provider);
            model.addAttribute("palletSpace", palletSpace);
            model.addAttribute("user", user.get());
            return "shop/basket";
        }
    }

    @PostMapping(path = "order")
    @ResponseBody
    public String placeOrder(@RequestParam Long orderId) {
        Order order = orderService.getOrderById(orderId);
        try {
            checkoutService.setOrderStatusOrdered(order);
            return "{\"message\":\"Order successful\"}";
        } catch (Exception e) {
            return "{\"error\":\"Order could not be placed. Please try again. If the issue is not resolved, please contact our team for support.\"}";
        }
    }

    @GetMapping(path = "order/confirmation")
    public String getOrderConfirmation(@RequestParam Long orderId, @RequestParam Long providerId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            if (providerId == null) {
                providerId = 2L;
            }
            Order order = orderService.getOrderById(orderId);
            List<Article> articles = orderService.getArticlesInOrder(user.get().getUserId());
            List<OrderItem> orderItems = orderService.getOrderItems(order);
            Float articlesTotalPrice = checkoutService.calculateTotalOrderPrice(order);
            Float totalPrice = checkoutService.calculateTotalOrderPriceWithShipping(order, providerId);
            Provider provider = providerService.getProviderById(providerId);
            float palletSpace = shippingCostCalculator.palletCalculation(order.getId());
            model.addAttribute("order", order);
            model.addAttribute("articles", articles);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("articlesTotalPrice", articlesTotalPrice);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("provider", provider);
            model.addAttribute("palletSpace", palletSpace);
            model.addAttribute("user", user.get());
            return "shop/orderConfirmation";
        }
    }
}
