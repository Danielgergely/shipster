package ch.shipster.service;

import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.OrderStatus;
import ch.shipster.data.repository.ArticleRepository;
import ch.shipster.data.repository.OrderItemRepository;
import ch.shipster.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Timo

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ArticleRepository articleRepository;

    //Get OrderItem if not exists

    public OrderItem getOrderItem(Long articleId, Long orderId) throws Exception {

        if (articleRepository.existsById(articleId)) {
            throw new Exception("Article ID (" + articleId + ") not found");
        } else if (orderRepository.existsById(orderId)) {
            throw new Exception("Order ID (" + orderId + ") not found");
        }

        OrderItem out = new OrderItem(articleId, orderId, 0);
        List<OrderItem> existingOI = orderItemRepository.getAllByArticleIdAndAndOrderId(articleId, orderId);

        if (existingOI.size() == 0) {
            if (orderRepository.getById(orderId).getOrderStatus() != OrderStatus.BASKET) {
                throw new Exception("Order Item for Article ID (" + articleId + ") does not exist for Order ID (" + orderId + ") and the order is not of the status BASKET");
            } else {
                orderItemRepository.save(out);
            }
        }
        if (existingOI.size() == 1) {
            out = existingOI.get(0);
        } else {
            throw new Exception("Multiple Order Items for same Article ID (" + articleId + ") and same Order ID (" + orderId + ")");
        }

        return out;
    }

    public OrderItem getOrderItem(Order order, Article article) throws Exception {
        return getOrderItem(order.getId(), article.getId());
    }


    // Add (or remove if negative)
    public void add(Article article, Order order, int inQuantity) throws Exception {

        if (order.getOrderStatus() != OrderStatus.BASKET) {
            throw new Exception("Order ID (" + order.getId() + ") is not of the Status BASKET. Quantity may not be changed");
        }

        OrderItem oi = getOrderItem(order, article);
        int newQuantity = oi.getQuantity() + inQuantity;

        if (newQuantity <= 0) {
            orderItemRepository.delete(oi);
        } else {
            oi.setQuantity(newQuantity);
        }
    }
    public void add(Long articleId, Long orderId, int inQuantity) throws Exception {
        add(articleRepository.getById(articleId), orderRepository.getById(orderId), inQuantity);
    }

    public void add(Article article, Order order) throws Exception {
        add(article, order, 1);
    }

    public void add(Long articleId, Long orderId) throws Exception {
        add(articleId, orderId, 1);
    }

    // Remove
    public void remove(Long articleId, Long orderId, int quantity) throws Exception {
        add(articleId, orderId, (- quantity));
    }

    public void remove(Long articleId, Long orderId) throws Exception {
        add(articleId, orderId, 1);
    }

    public void removeAll(Long articleId, Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElse(null);
        List<OrderItem> oiList = orderItemRepository.getAllByArticleIdAndAndOrderId(articleId, orderId);

        if (order == null) {
            throw new Exception("Order ID (" + orderId + ") not found");
        } else if (oiList.size() == 0) {

        } else if (order.getOrderStatus() != OrderStatus.BASKET) {
            throw new Exception("Order ID (" + orderId + ") is not of the Status BASKET. Quantity may not be changed");
        } else {
            orderItemRepository.deleteAll(oiList);
        }
    }

    /// Get related Article and Order

    public Article getArticle(Long orderItemId){
        return  getArticle(orderItemRepository.findById(orderItemId).orElseThrow());
    }

    public Article getArticle(OrderItem orderItem) {
        return articleRepository.findById(orderItem.getArticleId()).orElseThrow();
    }

    public Order getOrder(Long orderItemId){
        return getOrder(orderItemRepository.findById(orderItemId).orElseThrow());
    }

    public Order getOrder(OrderItem orderItem) {
        return orderRepository.findById(orderItem.getOrderId()).orElseThrow();
    }
}
