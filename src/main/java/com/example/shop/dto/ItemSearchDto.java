package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    // Search item by time it has been added ( all / 1d / 1w / 1m / 6m )
    private String searchDateType;

    // SELL / SOLD_OUT
    private ItemSellStatus searchSellStatus;

    // search item by itemName, createdBy
    private String searchBy;

    private String searchQuery = "";
}
