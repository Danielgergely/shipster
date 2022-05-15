package ch.shipster.service;

import ch.shipster.data.domain.*;
import ch.shipster.data.repository.AddressRepository;
import ch.shipster.data.repository.OrderRepository;
import ch.shipster.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.getAllByUserId(userId);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus){
        return orderRepository.getAllByOrderStatus(orderStatus.name());
    }

    /// Get Basket (order with state casket)
    public Order getBasketByUser(Long userId) throws Exception {
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
        return getBasketByUser(user.getUserId());
    }

    /// get related User
    public User getUser(Order order) {
        return userRepository.getById(order.getUserId());
    }

    /// get related User Address
    public Address getUserAddress(Order order) {
        return addressRepository.findById(userRepository.getById(order.getUserId()).getAddressId()).orElseThrow();
    }

    public Address getUserAddress(Long orderId){
        return getUserAddress(orderRepository.getById(orderId));
    }

    /// Get related Order Items
    public List<OrderItem> getOrderItems(Order order) {
        return orderItemService.orderItemRepository.getAllByOrderId(order.getId());
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return getOrderItems(orderRepository.getById(orderId));
    }

    public List<OrderItem> getOrderItemsInBasket(Long userId) throws Exception {
        return getOrderItems(getBasketByUser(userId));
    }

    public List<OrderItem> getOrderItemsInBasket(User user) throws Exception {
        return getOrderItems(getBasketByUser(user.getUserId()));
    }

    //Jonas
    /// Save Orders
    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId){
        return orderRepository.getById(orderId);
    }


}
