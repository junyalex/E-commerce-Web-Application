package com.example.shop.controller;

import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String mainPage(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 1);
        return "index";
    }

    @GetMapping("/search/items")
    public String searchItems(@RequestParam("query") String query, Model model) {
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchQuery(query);
        Pageable pageable = PageRequest.of(0, 6); // Show up to 6 results
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        return "fragments/header :: searchResults"; // Return only the searchResults fragment
    }


}
