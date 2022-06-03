package ch.shipster.data.repository;

import ch.shipster.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Timo

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByUserId(Long Id);

    List<Order> getAllByUserIdAndOrderStatus(Long userId, String orderStatus);

    Order getOrderByUserIdAndOrderStatus(Long userId, String orderStatus);

    List<Order> getAllByOrderStatus(String orderStatus);

}
