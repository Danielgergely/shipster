package ch.shipster.data.domain;

import ch.shipster.data.repository.ArticleRepository;
import ch.shipster.service.OrderItemService;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// Timo
// Intermediate Class for n:m relation between Order and Article

@Entity
@Table(name="order_item")
public class OrderItem {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes
    private long articleId;
    private long orderId;
    private int quantity;


    /// Constructor
    public OrderItem(Article article, Order order, int quantity){
        this.articleId = article.getId();
        this.orderId = order.getId();
        this.quantity = quantity;
    }
    public OrderItem(long articleId, long orderId, int quantity){
        this.articleId = articleId;
        this.orderId = orderId;
        this.quantity = quantity;
    }
    public OrderItem(){}

    /// Methods

    /// Special Getter & Setter

    /// Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
