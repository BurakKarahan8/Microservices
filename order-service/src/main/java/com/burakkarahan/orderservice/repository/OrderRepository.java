package com.burakkarahan.orderservice.repository;

import com.burakkarahan.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}