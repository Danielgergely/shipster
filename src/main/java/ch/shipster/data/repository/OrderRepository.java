package ch.shipster.data.repository;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Order;
import ch.shipster.data.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Timo

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsById(String id);

    Optional<Order> findById(String orderId);

    List<Order> getAllByUserId(String Id);

    List<Order> getAllByUserIdAndOrderStatus(String userId, String orderStatus);

    List<Order> getAllByOrderStatus(String orderStatus);

}
