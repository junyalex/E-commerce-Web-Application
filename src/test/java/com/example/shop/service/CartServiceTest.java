package com.example.shop.service;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.CartItemDto;
import com.example.shop.entity.CartItem;
import com.example.shop.entity.Item;
import com.example.shop.entity.Member;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    public void addCart(){
        // Creates Member, Item entity
        Member member = saveMember();
        Item item = saveItem();

        // Creates CartItemDto
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemId(item.getId());
        cartItemDto.setCount(3);

        // Adds CartItemDto to Cart
        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        // Tests if id and count are equal
        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());

    }



    public Item saveItem(){
        Item item = new Item();
        item.setItemName("test item");
        item.setPrice(BigDecimal.ONE);
        item.setItemDetail("test item detail");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(1000);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }


}