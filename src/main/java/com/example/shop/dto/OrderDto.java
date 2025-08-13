package com.example.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDto {

    @NotNull(message = "itemId is required")
    private Long itemId;

    @Min(value = 1, message="You need to add at least 1 item.")
    @Max(value = 99, message="You can order up to 99 items.")
    private int count;
}
