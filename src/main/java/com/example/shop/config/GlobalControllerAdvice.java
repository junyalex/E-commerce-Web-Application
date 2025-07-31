package com.example.shop.config;

import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final ItemService itemService;

    @ModelAttribute("itemSearchDto")
    public ItemSearchDto itemSearchDto() {
        return new ItemSearchDto();
    }

    @ModelAttribute("items")
    public Page<MainItemDto> items() {
        ItemSearchDto searchDto = new ItemSearchDto();
        Pageable pageable = PageRequest.of(0, 6);
        return itemService.getMainItemPage(searchDto, pageable);
    }
}