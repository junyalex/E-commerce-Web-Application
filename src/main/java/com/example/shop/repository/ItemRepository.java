package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom, QuerydslPredicateExecutor<Item> {

    List<Item> findByItemName(String itemName);

    List<Item> findByPriceLessThan(Integer price);
}
