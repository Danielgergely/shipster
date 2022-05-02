package ch.shipster.data.domain;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

// Timo
// Intermediate Class for n:m relation between Order and Article

@Entity
@Table(name="orderItem")
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
    public OrderItem(@NotNull Article article, @NotNull Order order, int quantity){
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
    // TODO addArticles
    // TODO removeArticles

    /// Special Getter & Setter
    //TODO getArticle()

    // TODO getOrder()

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
