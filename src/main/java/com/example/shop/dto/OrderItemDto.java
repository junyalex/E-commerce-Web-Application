package com.example.shop.dto;

import com.example.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Dto used for showing items that has been placed in order
 */
@Getter @Setter
public class OrderItemDto {

    private String itemName;
    private int count;
    private BigDecimal orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    /**
     * @return ( price x quantity ) of ordered Item.
     */
    public BigDecimal getTotalPrice() {
        return orderPrice.multiply(BigDecimal.valueOf(count));
    }

}
