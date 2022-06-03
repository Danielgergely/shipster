package ch.shipster.data.repository;

import ch.shipster.data.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Timo

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> getAllByArticleIdAndAndOrderId(Long articleId, Long orderId);

    List<OrderItem> getAllByArticleId(Long articleId);

    List<OrderItem> getAllByOrderId(Long orderId);

}
