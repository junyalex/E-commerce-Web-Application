package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="cart_item")
public class CartItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)  // A cart can contain multiple items
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch=FetchType.LAZY) // An item can be stored in more than a cart
    @JoinColumn(name="item_id")
    private Item item;

    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }
    public void addCount(int count) {
        this.count += count;
    }
}
