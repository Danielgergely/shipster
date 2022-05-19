package ch.shipster.data.domain;


import ch.shipster.data.repository.OrderItemRepository;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

//Timo

@Entity
@Table(name="shipster_order")
public class Order {

    /// ID
    @Id
    @GeneratedValue(generator = "orderId-generator")
    @GenericGenerator(name = "orderId-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "OI"),
            strategy = "ch.shipster.util.ShipsterIdGenerator")
    private String id;

    /// Attributes
    private String userId;
    private String orderStatus;

    private Date lastUpdateDate;
    private Date basketDate;
    private Date orderDate;
    private Date shippingDate;
    private Date deliveryDate;
    private Date cancellationDate;

    /// Constructor

    public Order(User user){
        this.userId = "UI_" + user.getUserId();
        this.orderStatus = OrderStatus.BASKET.name();
        this.lastUpdateDate = new Date();
        this.basketDate = new Date();
        this.orderDate = new Date(0);
        this.shippingDate = new Date(0);
        this.deliveryDate = new Date(0);
        this.cancellationDate = new Date(0);

    }

    public Order(Long userId){
        this.userId = "UI_" + userId;
        this.orderStatus = OrderStatus.BASKET.name();
        this.lastUpdateDate = new Date();
        this.basketDate = new Date();
        this.orderDate = new Date(0);
        this.shippingDate = new Date(0);
        this.deliveryDate = new Date(0);
        this.cancellationDate = new Date(0);
    }

    public Order(String userId){
        this.userId = userId;
        this.orderStatus = OrderStatus.BASKET.name();
        this.lastUpdateDate = new Date();
        this.basketDate = new Date();
        this.orderDate = new Date(0);
        this.shippingDate = new Date(0);
        this.deliveryDate = new Date(0);
        this.cancellationDate = new Date(0);
    }
    public Order(){}

    /// Methods


    /// Special Getter & Setter
    //TODO getUser (Instead of getUserId)
    //Start Jonas
    public OrderStatus getOrderStatus() /*throws IllegalArgumentException*/{
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus inOrderStatus) {
        this.orderStatus = inOrderStatus.name();
    }
    //End Jonas

    /// Getter & Setter
    public Long getId() {
        return Long.parseLong(id.substring(id.indexOf("_")+1));
    }

    public String getFullId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = "UI_" + userId;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getBasketDate() {
        return basketDate;
    }

    public void setBasketDate(Date basketDate) {
        this.basketDate = basketDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }
}
