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
    OrderItemService oiService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    public Order getBasketByUser(Long userId) throws Exception {
        Order outOrder = new Order(userId);
        List<Order> existingBaskets = orderRepository.getAllByUserIdAndOrderStatus(userId, OrderStatus.BASKET);

        if (existingBaskets.size() == 0) {
            orderRepository.save(outOrder);
        } else if (existingBaskets.size() == 1) {
            outOrder = existingBaskets.get(0);
        } else {
            throw new Exception("Multiple Baskets for same User with Id: " + userId);
        }
        return outOrder;
    }

    public Order getBasketByUser(User user) throws Exception {
        return getBasketByUser(user.getUserId());
    }

    public User getUser(Order order) {
        return userRepository.getById(order.getUserId());
    }

    public Address getUserAddress(Order order) {
        return getUserAddress(order.getId());
        }

    public Address getUserAddress(Long orderId){
        return addressRepository.findById(userRepository.getById(orderRepository.getById(orderId).getUserId()).getAddressId()).orElseThrow();
    }


    public List<OrderItem> getOrderItems(Order order) {
        return oiService.orderItemRepository.getAllByOrderId(order.getId());
    }

    // ToDo: Handle optionals
    public List<OrderItem> getOrderItems(Long orderId) {
        return getOrderItems(orderRepository.getById(orderId));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.getAllByUserId(userId);
    }
    //Jonas
    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId){
        return orderRepository.getById(orderId);
    }


}
