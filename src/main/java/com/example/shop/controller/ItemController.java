package com.example.shop.controller;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        // When ADMIN did not put all information for item registration, stay on same page.
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        // Redirects to same page if ADMIN did not add any image
        if (itemImgFileList.getFirst().isEmpty() && itemFormDto == null) {
            model.addAttribute("errorMessage", "You must add at least one image");
            return "item/itemForm";
        }

        // Limit the number of uploaded files to 5
        if (itemImgFileList.size() > 5) {
            model.addAttribute("errorMessage", "You can upload a maximum of 5 images.");
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


}
