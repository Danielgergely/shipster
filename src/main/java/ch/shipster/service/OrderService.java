package ch.shipster.service;

import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.OrderStatus;
import ch.shipster.data.domain.User;
import ch.shipster.data.repository.OrderItemRepository;
import ch.shipster.data.repository.OrderRepository;
import org.checkerframework.checker.units.qual.A;
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

    public List<OrderItem> getOrderItems(Order order) {
        return oiService.oiRepository.getAllByOrderId(order.getId());
    }

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



}
