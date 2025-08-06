package com.example.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {

    @NotNull(message="Item Id is required")
    private Long itemId;

    @Min(value=1, message="You must add at least 1 item")
    @Max(value=99, message="You can add up to 999 items")
    private int count;
}
