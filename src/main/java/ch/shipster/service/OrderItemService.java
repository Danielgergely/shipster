package ch.shipster.service;

import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.OrderStatus;
import ch.shipster.data.repository.ArticleRepository;
import ch.shipster.data.repository.OrderItemRepository;
import ch.shipster.data.repository.OrderRepository;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.exceptions.PalletOverloadException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Timo

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ShippingCostCalculator  shippingCostCalculator;

    @Autowired
    OrderService orderService;

    public List<OrderItem> getAllByOrderId(Long orderId){
        return orderItemRepository.getAllByOrderId(orderId);
    }

    //Get OrderItem if not exists

    public OrderItem getOrderItem(Long articleId, Long orderId) throws Exception {

        if (!articleRepository.existsById(articleId)) {
            ShipsterLogger.logger.error("Article ID (" + articleId + ") not found");
            throw new Exception("Article ID (" + articleId + ") not found");
        } else if (!orderRepository.existsById(orderId)) {
            ShipsterLogger.logger.error("Order ID (" + orderId + ") not found");
            throw new Exception("Order ID (" + orderId + ") not found");
        }

        OrderItem out = new OrderItem(articleId, orderId, 0);
        List<OrderItem> existingOI = orderItemRepository.getAllByArticleIdAndAndOrderId(articleId, orderId);

        if (existingOI.size() == 0) {
            if (orderRepository.getById(orderId).getOrderStatus() != OrderStatus.BASKET) {
                ShipsterLogger.logger.error("Order Item for Article ID (" + articleId + ") does not exist for Order ID (" + orderId + ") and the order is not of the status BASKET");
                throw new Exception("Order Item for Article ID (" + articleId + ") does not exist for Order ID (" + orderId + ") and the order is not of the status BASKET");
            } else {
                orderItemRepository.save(out);
            }
        }
        else if (existingOI.size() == 1) {
            out = existingOI.get(0);
        } else {
            ShipsterLogger.logger.error("Multiple Order Items for same Article ID (" + articleId + ") and same Order ID (" + orderId + ")");
            throw new Exception("Multiple Order Items for same Article ID (" + articleId + ") and same Order ID (" + orderId + ")");
        }

        return out;
    }

    public OrderItem getOrderItem(Order order, Article article) throws Exception {
        return getOrderItem(article.getId(), order.getId());
    }


    // Add (or remove if negative)
    public Optional<OrderItem> add(Article article, Order order, int inQuantity) throws Exception, PalletOverloadException {

        if (order.getOrderStatus() != OrderStatus.BASKET) {
            ShipsterLogger.logger.error("Order ID (" + order.getId() + ") is not of the Status BASKET. Quantity may not be changed");
            throw new Exception("Order ID (" + order.getId() + ") is not of the Status BASKET. Quantity may not be changed");
        }



        OrderItem oi = getOrderItem(order, article);
        int newQuantity = oi.getQuantity() + inQuantity;


        if (newQuantity <= 0) {
            orderItemRepository.delete(oi);
            return Optional.empty();
        } else {
            List<OrderItem> tempListOI = orderService.getOrderItems(order);

            for(OrderItem xoi : tempListOI){
                if(xoi.getArticleId() == article.getId() && xoi.getOrderId() == order.getId()){
                    xoi.setQuantity(xoi.getQuantity() + inQuantity);
                } else {
                    tempListOI.add(new OrderItem(article.getId(), order.getId(), inQuantity));
                }
            }

            if (shippingCostCalculator.spaceLimit(shippingCostCalculator.requiredPallets(tempListOI))) {
                oi.setQuantity(newQuantity);
                return Optional.of(orderItemRepository.save(oi));
            } else {
                throw new PalletOverloadException("This would overload the truck");
            }

        }
    }
    public Optional<OrderItem> add(Long articleId, Long orderId, int inQuantity) throws Exception {
        return add(articleRepository.getById(articleId), orderRepository.getById(orderId), inQuantity);
    }

    public Optional<OrderItem> add(Article article, Order order) throws Exception {
        return add(article, order, 1);
    }

    public Optional<OrderItem> add(Long articleId, Long orderId) throws Exception {
        return add(articleId, orderId, 1);
    }

    // Set
    public Optional<OrderItem> set(Article article, Order order, int quantity) throws Exception {
        if (order.getOrderStatus() != OrderStatus.BASKET) {
            throw new Exception("Order ID (" + order.getId() + ") is not of the Status BASKET. Quantity may not be changed");
        }

        OrderItem orderItem = getOrderItem(order, article);

        if (quantity <= 0) {
            removeAll(article, order);
            return Optional.empty();
        } else  {
            orderItem.setQuantity(quantity);
            return Optional.of(orderItemRepository.save(orderItem));
        }

    }

    public Optional<OrderItem> set(Long articleId, Long orderId, int quantity) throws Exception {
        return set(articleRepository.getById(articleId), orderRepository.getById(orderId), quantity);
    }

    // Remove
    public void remove(Long articleId, Long orderId, int quantity) throws Exception {
        add(articleId, orderId, (- quantity));
    }

    public void remove(Long articleId, Long orderId) throws Exception {
        add(articleId, orderId, -1);
    }

    public void removeAll(Long articleId, Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElse(null);
        List<OrderItem> oiList = orderItemRepository.getAllByArticleIdAndAndOrderId(articleId, orderId);

        if (order == null) {
            ShipsterLogger.logger.error("Order ID (" + orderId + ") not found");
            throw new Exception("Order ID (" + orderId + ") not found");
        } else if (oiList.size() == 0) {

        } else if (order.getOrderStatus() != OrderStatus.BASKET) {
            ShipsterLogger.logger.error("Order ID (" + orderId + ") is not of the Status BASKET. Quantity may not be changed");
            throw new Exception("Order ID (" + orderId + ") is not of the Status BASKET. Quantity may not be changed");
        } else {
            orderItemRepository.deleteAll(oiList);
        }
    }

    public void removeAll(Article article, Order order) throws Exception {
        removeAll(article.getId(), order.getId());
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

    public void deleteOrderItemById(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    public void deleteOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = getAllByOrderId(orderId);
        for(OrderItem orderItem : orderItems) {
            deleteOrderItemById(orderItem.getId());
        }
    }
}
