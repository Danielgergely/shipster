package ch.shipster.data.domain;

import javax.persistence.*;
import java.util.Date;

//Timo

@Entity
@Table(name = "shipster_order")
public class Order {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes
    private Long userId;
    private String orderStatus;

    private Date lastUpdateDate;
    private Date basketDate;
    private Date orderDate;
    private Date shippingDate;
    private Date deliveryDate;
    private Date cancellationDate;

    private Long providerId;

    /// Constructor

    public Order(User user) {
        this.userId = user.getUserId();
        this.orderStatus = OrderStatus.BASKET.name();
        this.lastUpdateDate = new Date();
        this.basketDate = new Date();
        this.orderDate = null;
        this.shippingDate = null;
        this.deliveryDate = null;
        this.cancellationDate = null;
        this.providerId = 1L;

    }

    public Order(long userId) {
        this.userId = userId;
        this.orderStatus = OrderStatus.BASKET.name();
        this.lastUpdateDate = new Date();
        this.basketDate = new Date();
        this.orderDate = null;
        this.shippingDate = null;
        this.deliveryDate = null;
        this.cancellationDate = null;
        this.providerId = 1L;
    }

    public Order() {
    }

    /// Methods


    /// Special Getter & Setter
    //TODO getUser (Instead of getUserId)
    //Start Jonas
    public OrderStatus getOrderStatus() /*throws IllegalArgumentException*/ {
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus inOrderStatus) {
        this.orderStatus = inOrderStatus.name();
    }
    //End Jonas

    /// Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
