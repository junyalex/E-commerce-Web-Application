package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="cart_item")
public class CartItem {

    @Id @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne  // A cart can contain multiple items
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne // An item can be stored in more than a cart
    @JoinColumn(name="item_id")
    private Item item;

    private int count;
}
