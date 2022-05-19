package ch.shipster.service;

import ch.shipster.data.domain.*;
import ch.shipster.data.repository.AddressRepository;
import ch.shipster.data.repository.OrderRepository;
import ch.shipster.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CollectionCertStoreParameters;
import java.util.*;

// Timo

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    /// Get Order Lists
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.getAllByUserId(userId);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.getAllByOrderStatus(orderStatus.name());
    }

    /// Get Basket (order with state Basket)
    public Order getBasketByUser(String userId) throws Exception {
        Order outOrder = new Order(userId);
        List<Order> existingBaskets = orderRepository.getAllByUserIdAndOrderStatus(userId, OrderStatus.BASKET.name());

        if (existingBaskets.size() == 0) {
            orderRepository.save(outOrder);
        } else if (existingBaskets.size() == 1) {
            outOrder = existingBaskets.get(0);
        } else {
            ShipsterLogger.logger.error("Multiple Baskets for same User with Id: " + userId);
            throw new Exception("Multiple Baskets for same User with Id: " + userId);
        }
        return outOrder;
    }

    public Order getBasketByUser(User user) throws Exception {
        return getBasketByUser(user.getFullUserId());
    }

    /// get related User
    public User getUser(Order order) {
        return userRepository.getByUserId(order.getUserId());
    }

    public User getUser(Long orderId) {
        return getUser(orderRepository.getById(orderId));
    }

    /// get related User Address
    public Address getUserAddress(Order order) {
        return addressRepository.findAddressById(userRepository.getByUserId(order.getUserId()).getAddressId()).orElseThrow();
    }

    public Address getUserAddress(String orderId) {
        return getUserAddress(orderRepository.findById(orderId).orElseThrow());
    }

    /// Get related Order Items
    public List<OrderItem> getOrderItems(Order order) {
        List<OrderItem> orderItems = orderItemService.getAllByOrderId(order.getFullId());
        orderItems.sort(Comparator.comparing(OrderItem::getArticleId));
        return orderItems;
    }

    public List<OrderItem> getOrderItems(String orderId) {
        return getOrderItems(orderRepository.findById(orderId).orElseThrow());
    }

    public List<OrderItem> getOrderItemsInBasket(String userId) throws Exception {
        return getOrderItems(getBasketByUser(userId));
    }

    public List<OrderItem> getOrderItemsInBasket(User user) throws Exception {
        return getOrderItems(getBasketByUser(user.getFullUserId()));
    }

    public List<Article> getArticlesInBasket(String userId) throws Exception {
        List<Article> outArticles = new ArrayList<>();

        for (OrderItem i : getOrderItemsInBasket(userId)) {
            outArticles.add(orderItemService.getArticle(i));
        }
        outArticles.sort(Comparator.comparing(Article::getId));
        return outArticles;
    }

    //Jonas
    /// Save Orders
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.getById(orderId);
    }


}
