package com.example.shop.controller;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.entity.Item;
import com.example.shop.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        model.addAttribute("errorMessage", null); // Add this line
        return "item/itemForm";
    }

    /**
     * This function is executed when ADMIN tries to add new Item
     */
    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList) {
        // When ADMIN d`id not put all information for item registration, stay on same page.
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        // When ADMIN did not put all information for item registration, stay on same page.
        // Redirects to same page if ADMIN did not add any image

        boolean hasValidImage = itemImgFileList.stream()
                .anyMatch(file -> file != null && !file.isEmpty());

        if (!hasValidImage) {
            bindingResult.rejectValue("itemImgDtoList", "required", "You must add at least one image");
        }
        if (itemImgFileList.size() > 5) {
            bindingResult.rejectValue("itemImgDtoList", "maxSize", "You can upload a maximum of 5 images");
        }
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }


        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "An error has occurred while saving the item.");
            return "item/itemForm";
        }

        // return to main page if item has been added successfully
        return "redirect:/";
    }

    /**
     * This function reads itemFormDto with given itemId, and add its attribute to model
     * @return model which contains read item's information
     */
    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) {

        try{
            ItemFormDto itemFormDto = itemService.getItemFormDto(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "Item not found.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }

    /**
     * Update item with information that ADMIN has chosen.
     */
    @PostMapping("admin/item/{itemId}")
    public String updateItem(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             Model model) {
        if(bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if(itemImgFileList.getFirst().isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "You must add at least one image");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch(Exception e){
            model.addAttribute("errorMessage", "An error has occurred while updating the item.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    @GetMapping(value={"/admin/items", "admin/items/{page}"})
    public String manageItem(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page,
                             Model model) {
        // starts with 0 page if page number is not specified, and shows 3 items per page
        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("pagedItems", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 3);
        return "item/itemMng";
    }



}
