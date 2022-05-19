package ch.shipster.data.domain;

import ch.shipster.data.repository.ArticleRepository;
import ch.shipster.service.OrderItemService;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "orderItemId-generator")
    @GenericGenerator(name = "orderItemId-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "OII"),
            strategy = "ch.shipster.util.ShipsterIdGenerator")
    private String id;

    /// Attributes
    private String articleId;
    private String orderId;
    private int quantity;


    /// Constructor
    public OrderItem(Article article, Order order, int quantity){
        this.articleId = "AI_" + article.getId();
        this.orderId = "OI_" + order.getId();
        this.quantity = quantity;
    }
    public OrderItem(Long articleId, Long orderId, int quantity){
        this.articleId = "AI_" + articleId;
        this.orderId = "OI_" + orderId;
        this.quantity = quantity;
    }

    public OrderItem(String articleId, String orderId, int quantity){
        this.articleId = articleId;
        this.orderId = orderId;
        this.quantity = quantity;
    }
    public OrderItem(){}

    /// Methods

    /// Special Getter & Setter

    /// Getter & Setter
    public Long getId() {
        if(id == null) {
            return null;
        }else {
            return Long.parseLong(id.substring(id.indexOf("_")+1));
        }
    }

    public String getFullId() {
        return id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = "AI_" + articleId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = "OI_" + orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
