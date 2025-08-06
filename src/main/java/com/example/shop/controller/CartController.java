package com.example.shop.controller;

import com.example.shop.dto.CartDetailDTo;
import com.example.shop.dto.CartItemDto;
import com.example.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Adds the specified item to the authenticated user’s shopping cart and returns the operation result.
     * @param cartItemDto   the DTO containing itemId and count to add
     * @param bindingResult the result of validating cartItemDto
     * @param principal     the security principal of the authenticated user
     * @return a ResponseEntity with the new CartItem ID and HTTP 200 on success,
     *         or an error message and HTTP 400 on validation or processing failure
     */
    @PostMapping("/cart")
    public @ResponseBody ResponseEntity order(
            @RequestBody @Valid CartItemDto cartItemDto,
            BindingResult bindingResult,
            Principal principal
            ){

        // Check if there is an error while binding data
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public String showCart(Principal principal, Model model){
        List<CartDetailDTo> cartItems = cartService.getCartItems(principal.getName());
        model.addAttribute("cartItems", cartItems);
        return "cart/cartList";
    }
}
