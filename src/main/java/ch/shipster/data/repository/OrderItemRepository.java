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

    // Not necessary, already done by the JpaRepository
    //@Override
    //Optional<OrderItem> findById(Long orderItemId);

    //@Override
    //List<OrderItem> findAll();

    //OrderItem save(OrderItem orderItem);

    List<OrderItem> getAllByArticleIdAndAndOrderId(Long articleId, Long orderId);

    List<OrderItem> getAllByArticleId(Long articleId);

    List<OrderItem> getAllByOrderId(Long orderId);

}
