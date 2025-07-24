package com.example.shop.repository;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
