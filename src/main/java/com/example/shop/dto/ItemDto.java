package com.example.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class ItemDto {

    private Long id;

    private String itemName;

    private BigDecimal price;

    private String itemDescription;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
