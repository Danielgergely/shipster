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

    List<Order> getAllByUserId(Long Id);

    List<Order> getAllByUserIdAndOrderStatus(Long orderId, OrderStatus orderStatus);

}
