package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message="This field is required")
    private String itemName;

    @NotNull(message="This field is required")
    private Integer price;

    @NotBlank(message="This field is required")
    private String itemDetail;

    @NotNull(message="This field is required")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // Converts ItemFormDto -> Item
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    // Converts Item -> ItemFormDto
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
