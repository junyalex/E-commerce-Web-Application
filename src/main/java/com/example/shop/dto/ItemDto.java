package com.example.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemDto {

    private Long id;

    private String itemName;

    private Integer price;

    private String itemDescription;

    private String sellStatCd;

    private LocalDateTime registerTime;

    private LocalDateTime updateTime;
}
