package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.ItemFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="item") @ToString
public class Item extends BaseEntity{

    @Id @Column(name="item_id") @GeneratedValue
    private Long id; // Primary key

    @Column(nullable = false, length=50)
    private String itemName; // Name of item

    @Column(name="price", nullable = false)
    private int price; // price

    @Column(nullable = false)
    private int stockNumber; // Number of stock available

    @Lob @Column(nullable = false)
    private String itemDetail; // Item description

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // SELL or SOLD_OUT

    public void updateItem(ItemFormDto itemFormDto){
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}
