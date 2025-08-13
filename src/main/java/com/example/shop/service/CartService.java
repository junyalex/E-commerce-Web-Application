package com.example.shop.service;

import com.example.shop.dto.CartDetailDTo;
import com.example.shop.dto.CartItemDto;
import com.example.shop.dto.CartOrderDto;
import com.example.shop.dto.OrderDto;
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
    private final OrderService orderService;
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

    /**
     * @param cartItemId : id of cartItem added by user
     * @param email : email of logged in member
     * @return {@code true} if the member owns the cart item; {@code false} otherwise
     */
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member member = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member member2 = cartItem.getCart().getMember();

        return member.getEmail().equals(member2.getEmail());
    }

    // Update cartItem's quantity in member's cart.
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    // Delete CartItem from CartItemRepository
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    /**
     * This function places an order and remove all items in cart has been ordered
     * @param cartOrderDtoList : A List containing cartOrderDto
     * @param email of logged in member
     * @return id of Order
     */
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        // Makes an order
        Long orderId = orderService.makeOrder(orderDtoList, email);

        // Delete all items in cart
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }


}
