package ch.shipster.service;


import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

//Jonas
@Service
public class CheckoutService {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ShippingCostCalculator shippingCostCalculator;

    public void setOrderStatusCancel(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.BASKET || order.getOrderStatus() == OrderStatus.ORDERED) {
            order.setOrderStatus(OrderStatus.CANCELED);
            order.setCancellationDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);
        } else {
            ShipsterLogger.logger.error("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to cancelled as the current Status is" + order.getOrderStatus().name());
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to cancelled as the current Status is" + order.getOrderStatus().name());

        }
    }

    public void setOrderStatusOrdered(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.BASKET) {
            order.setOrderStatus(OrderStatus.ORDERED);
            order.setOrderDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);
        } else {
            ShipsterLogger.logger.error("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + " cannot be set to ordered as the current Status is " + order.getOrderStatus().name());
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + " cannot be set to ordered as the current Status is " + order.getOrderStatus().name());
        }
    }

    public void setOrderStatusShipped(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.ORDERED) {
            order.setOrderStatus(OrderStatus.SHIPPED);
            order.setShippingDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);
        } else {
            ShipsterLogger.logger.error("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + " cannot be set to Shipped as the current Status is " + order.getOrderStatus().name());
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + " cannot be set to Shipped as the current Status is " + order.getOrderStatus().name());
        }
    }

    public void setOrderStatusDelivered(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.SHIPPED) {
            order.setOrderStatus(OrderStatus.DELIVERED);
            order.setDeliveryDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);

        } else {
            ShipsterLogger.logger.error("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to Delivered as the current Status is" + order.getOrderStatus().name());
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + " cannot be set to Delivered as the current Status is " + order.getOrderStatus().name());
        }


    }

    /// Calculate Total Order Price withOUT Shipping
    public float calculateTotalOrderPrice(Order order) {
        float price = 0;
        for (OrderItem o : orderService.getOrderItems(order)) {
            float intermediary = orderItemService.getArticle(o).getPrice() * o.getQuantity();
            price = price + intermediary;
        }

        return price;
    }

    public float calculateTotalOrderPrice(Long orderId) {
        return calculateTotalOrderPrice(orderService.getOrderById(orderId));
    }

    /// Calculate Total Order Price with Shipping
    public float calculateTotalOrderPriceWithShipping(Order order) throws IOException, InterruptedException {
        float price = calculateTotalOrderPrice(order);
        float shippingCost = 0;
        if (orderService.getOrderItems(order).size() != 0)
            shippingCost = shippingCostCalculator.costCalculation(order.getId());
        return price + shippingCost;
    }

    public float calculateTotalOrderPriceWithShipping(Long orderId) throws IOException, InterruptedException {
        return calculateTotalOrderPriceWithShipping(orderService.getOrderById(orderId));
    }

    public float calculateTotalOrderPriceWithShipping(Order order, Long providerId) throws IOException, InterruptedException {
        float price = calculateTotalOrderPrice(order);
        if (orderService.getOrderItems(order).size() != 0) {
            price += shippingCostCalculator.costCalculation(order.getId(), providerId);
        }
        return price;
    }

    public float calculateTotalOrderPriceWithShipping(Long orderId, Long providerId) throws IOException, InterruptedException {
        return calculateTotalOrderPriceWithShipping(orderService.getOrderById(orderId), providerId);
    }


}
