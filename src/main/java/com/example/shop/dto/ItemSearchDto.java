package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.constant.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    // Search item by time it has been added ( all / 1d / 1w / 1m / 6m )
    private String searchDateType;

    // SELL or SOLD_OUT
    private ItemSellStatus searchSellStatus;

    private ItemType searchItemType;

    // search item by itemName, createdBy
    private String searchBy;

    private String searchQuery = "";
}
