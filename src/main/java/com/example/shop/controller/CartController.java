package com.example.shop.controller;

import com.example.shop.dto.CartDetailDTo;
import com.example.shop.dto.CartItemDto;
import com.example.shop.dto.CartOrderDto;
import com.example.shop.entity.CartItem;
import com.example.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody ResponseEntity<?> order(
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
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @GetMapping("/cart")
    public String showCart(Principal principal, Model model){
        List<CartDetailDTo> cartItems = cartService.getCartItems(principal.getName());
        model.addAttribute("cartItems", cartItems);
        return "cart/cartList";
    }

    /**
     * @return ResponseEntity<Long cartItemId> if count has been updated successfully,
     *         else ResponseEntity<String errorMessage>
     */
    @PatchMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> updateCartItem(
            @PathVariable("cartItemId") Long cartItemId, int count, Principal principal
    ){
        if(count <= 0){
            return new ResponseEntity<String>("Select at least one item", HttpStatus.BAD_REQUEST);
        } else if (cartService.validateCartItem(cartItemId, principal.getName())) {
            // if member is validated, update count of CartItem
            cartService.updateCartItemCount(cartItemId, count);
            return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
        }
        // member is not validated
        return new ResponseEntity<String>("No Authorization", HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete Item from Cart
     * @param cartItemId that user wants to remove from cart
     * @return 200 OK if id has deleted successfully or 403 Forbidden if authorization fails
     */
    @DeleteMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> deleteCartItem(
            @PathVariable("cartItemId") Long cartItemId,
            Principal principal){

        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("No access to this change", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PostMapping("/cart/orders")
    public @ResponseBody ResponseEntity<?> orderCartItem(
            @RequestBody CartOrderDto cartOrderDto, Principal principal
    ){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.isEmpty()) {
            return new ResponseEntity<String>("Add at least one item to cart", HttpStatus.FORBIDDEN);
        }

        for(CartOrderDto cartOrder : cartOrderDtoList){
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("Authorization Failed", HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
