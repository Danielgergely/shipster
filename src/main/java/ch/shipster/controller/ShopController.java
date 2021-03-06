package ch.shipster.controller;

import ch.shipster.data.domain.*;
import ch.shipster.exceptions.PalletOverloadException;
import ch.shipster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    ReceiptGenerator receiptGenerator;

    @GetMapping(path = "shop")
    public String getShopView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            model.addAttribute("user", "no_user");
        } else {
            model.addAttribute("user", user.get());
        }
        List<Article> products = articleService.getAllArticles();
        model.addAttribute("products", products);
        return "shop/shop";
    }

    @PostMapping(path = "shop/basket/add")
    @ResponseBody
    public String addToBasket(@RequestParam Long articleId) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "{\"message\": \"please log in to add to basket\"}";
        } else {
            Order order = orderService.getBasketByUser(user.get());
            orderItemService.add(articleId, order.getId());
            ShipsterLogger.logger.info("Item " + articleId + " added to order " + order.getId());
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
    public String addArticle(@RequestParam Long articleId, @RequestParam Long orderId, RedirectAttributes redirectAttributes) throws Exception {
        try {
            orderItemService.add(articleId, orderId);
        } catch (PalletOverloadException e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:/shop/basket";
        }
        redirectAttributes.addAttribute("message", "Product added to basket");
        ShipsterLogger.logger.info("Article " + articleId + " added to basket with id: " + orderId);
        return "redirect:/shop/basket";
    }

    @GetMapping(path = "shop/article/remove")
    public String removeArticle(@RequestParam Long articleId, @RequestParam Long orderId, RedirectAttributes redirectAttributes) throws Exception {
        orderItemService.remove(articleId, orderId);
        redirectAttributes.addAttribute("message", "Product removed from basket");
        ShipsterLogger.logger.info("Article " + articleId + " removed from basket with id: " + orderId);
        return "redirect:/shop/basket";
    }


    @GetMapping(path = "shop/basket")
    public String getBasket(@RequestParam(required = false) String message, Model model) throws Exception {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            if (Objects.equals(message, "Product added to basket")) {
                model.addAttribute("successMessage", message);
            } else if (Objects.equals(message, "Product removed from basket")) {
                model.addAttribute("successMessage", message);
            } else if (message != null) {
                model.addAttribute("errorMessage", message);
            }
            Order order = orderService.getBasketByUser(user.get());
            Long providerId = order.getProviderId();
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
        try {
            Order order = orderService.getOrderById(orderId);
            checkoutService.setOrderStatusOrdered(order);
        } catch (Exception e) {
            return "{\"message\": \"" + e.getMessage() + "\"}";
        }
        ShipsterLogger.logger.info("Basket with id: " + orderId + " ordered");
        return "{\"message\": \"Basket with id: " + orderId + " ordered\"}";
    }

    @GetMapping(path = "/shop/provider")
    public String changeProvider(@RequestParam Long providerId, @RequestParam Long orderId) {
        orderService.changeProvider(orderId, providerId);
        ShipsterLogger.logger.info("Provider for order " + orderId + " changed to: " + providerId);
        return "redirect:/shop/basket";
    }

    @GetMapping(path = "order/confirmation")
    public String getOrderConfirmation(@RequestParam(name = "orderId") Long orderId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Order order = orderService.getOrderById(orderId);
            Long providerId = order.getProviderId();
            List<Article> articles = orderService.getArticlesInOrder(orderId);
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

    @GetMapping(path = "/myOrders")
    public String getUserOrders(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            List<Order> orders = orderService.getOrdersByUserNotBasket(user.get().getUserId());
            model.addAttribute("orders", orders);
            model.addAttribute("user", user.get());
            return "shop/myOrders";
        }
    }

    @GetMapping(path = "/order")
    public String getOrderDetails(@RequestParam Long orderId, Model model) throws IOException, InterruptedException {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Order order = orderService.getOrderById(orderId);
            List<OrderItem> orderItems = orderItemService.getAllByOrderId(orderId);
            List<Article> articles = orderService.getArticlesInOrder(orderId);
            Provider provider = providerService.getProviderById(order.getProviderId());
            Float articlesTotalPrice = checkoutService.calculateTotalOrderPrice(order);
            Float totalPrice = checkoutService.calculateTotalOrderPriceWithShipping(order, order.getProviderId());
            model.addAttribute("user", user.get());
            model.addAttribute("provider", provider);
            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("articles", articles);
            model.addAttribute("articlesTotalPrice", articlesTotalPrice);
            model.addAttribute("totalPrice", totalPrice);
            return "shop/order";
        }
    }

    //Manuel
    @GetMapping(path = "order/receipt")
    public void generateReceipt(HttpServletResponse response, @RequestParam Long orderId) throws IOException, InterruptedException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=shipster_receipt_#" + orderId + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptGenerator.createPDF(response, orderId);
        ShipsterLogger.logger.info("Receipt for order  " + orderId + " generated.");
    }

}
