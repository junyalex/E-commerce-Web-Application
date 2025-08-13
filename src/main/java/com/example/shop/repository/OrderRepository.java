package com.example.shop.repository;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * find all orders has been placed by logged in member
     * @param email of member
     * @return List of all orders
     */
    @Query("select o from Order o " + "where o.member.email = :email " +
            "order by o.orderDate desc")
    List<Order> findOrders(@Param("email") String email);
}
