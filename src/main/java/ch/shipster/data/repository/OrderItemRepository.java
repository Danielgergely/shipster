package ch.shipster.data.repository;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.OrderItem;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Timo

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> getAllByArticleIdAndAndOrderId(String articleId, String orderId);

    List<OrderItem> getAllByArticleId(String articleId);

    List<OrderItem> getAllByOrderId(String orderId);

}
