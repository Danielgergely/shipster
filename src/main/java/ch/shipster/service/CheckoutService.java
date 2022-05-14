package ch.shipster.service;



import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

//Jonas
@Service
public class CheckoutService {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    public void setOrderStatusCancel(Order order) throws Exception {
      if (order.getOrderStatus() == OrderStatus.BASKET || order.getOrderStatus() == OrderStatus.ORDERED){
          order.setOrderStatus(OrderStatus.CANCELED);
          order.setCancellationDate(new Date());
          order.setLastUpdateDate(new Date());
          orderService.saveOrder(order);
      } else{
          throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                  " from User with ID " + order.getUserId() + "cannot be set to cancelled as the current Status is" + order.getOrderStatus().name());
      }
    }
    public void setOrderStatusOrdered(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.BASKET){
            order.setOrderStatus(OrderStatus.ORDERED);
            order.setOrderDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);
        } else {
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to ordered as the current Status is" + order.getOrderStatus().name());
        }
    }

    public void setOrderStatusShipped(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.ORDERED){
            order.setOrderStatus(OrderStatus.SHIPPED);
            order.setShippingDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);
        } else {
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to Shipped as the current Status is" + order.getOrderStatus().name());
        }
    }
    public void setOrderStatusDelivered(Order order) throws Exception {
        if (order.getOrderStatus() == OrderStatus.SHIPPED){
            order.setOrderStatus(OrderStatus.DELIVERED);
            order.setDeliveryDate(new Date());
            order.setLastUpdateDate(new Date());
            orderService.saveOrder(order);

        }else{
            throw new Exception("The Order " + order.getId() + " in Status " + order.getOrderStatus().name() +
                    " from User with ID " + order.getUserId() + "cannot be set to Delivered as the current Status is" + order.getOrderStatus().name());
        }


    }

    public float calculateTotalOrderPrice(Order order) {
        float price = 0;
        for(OrderItem o : orderService.getOrderItems(order)){
            float intermediary = orderItemService.getArticle(o).getPrice() * o.getQuantity();
            price = price + intermediary;
        }

        return price;
    }
    public float calculateTotalOrderPriceWithShipping(Order order){
       //TODO After merge from Manuel, activitate this method
        float price = calculateTotalOrderPrice(order) +

        return price;
    }




}
