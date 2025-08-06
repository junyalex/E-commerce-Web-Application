package com.example.shop.dto;

import com.example.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CartDetailDTo {

    private Long cartItemId;

    private String itemName;

    private BigDecimal price;

    private int count;

    private String imgUrl;

    private Long itemId;

    public CartDetailDTo(Long cartItemId, String itemName, BigDecimal price, int count, String imgUrl, Long itemId) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
        this.itemId = itemId;
    }

}
