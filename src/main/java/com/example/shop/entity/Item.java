package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.constant.ItemType;
import com.example.shop.dto.ItemFormDto;
import com.example.shop.exception.OutofStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="item") @ToString
public class Item extends BaseEntity{

    @Id @Column(name="item_id") @GeneratedValue
    private Long id; // Primary key

    @Column(nullable = false, length=50)
    private String itemName; // Name of item

    @Column(name="price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // price

    @Column(nullable = false)
    private int stockNumber; // Number of stock available

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String itemDetail; // Item description

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // SELL or SOLD_OUT

    @Enumerated(EnumType.STRING)
    private ItemType itemType; // Type of item

    public void updateItem(ItemFormDto itemFormDto){
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        this.itemType = itemFormDto.getItemType();
    }

    /**
     * When an order is placed, this function removes number of stocks left
     * @param stockNumber : number of stock that order has been placed.
     */
    public void removeStock(int stockNumber){
        int remainStock = this.stockNumber - stockNumber;
        if(remainStock < 0){
            throw new OutofStockException("The product is out of stock. Current stock remaining : " + remainStock);
        }
        this.stockNumber = remainStock;
    }

    /**
     * When an order is cancelled, add stock back to item.
     */
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
