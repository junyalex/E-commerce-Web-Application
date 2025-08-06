package com.example.shop.service;

import com.example.shop.dto.CartDetailDTo;
import com.example.shop.dto.CartItemDto;
import com.example.shop.entity.Cart;
import com.example.shop.entity.CartItem;
import com.example.shop.entity.Item;
import com.example.shop.entity.Member;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    /**
     * Adds CartItem to Cart
     * @param cartItemDto which contains information of added item by user
     * @param email of user
     * @return CartItem id of added item
     */
    public Long addCart(CartItemDto cartItemDto, String email) {

        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        // if member is adding item to cart for the first time, create new Cart entity
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // Checks if item is already added to cart
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDto.getItemId());
        // If item already exists in cart, increment count
        if(savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            // If not, create new CartItem entity
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    /**
     * @param email of logged in member
     * @return List of CartDetailDto which contains items that member has added to cart
     */
    @Transactional(readOnly = true)
    public List<CartDetailDTo> getCartItems(String email) {

        List<CartDetailDTo> cartDetailDToList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        // cart == null when user has never added item to cart
        if(cart == null){
            return cartDetailDToList;
        }

        return cartItemRepository.findCartDetailDtoByCartId(cart.getId());
    }
}
