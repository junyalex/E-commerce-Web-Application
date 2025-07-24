package com.example.shop.repository;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Testing : Save item")
    public void createItemTest(){
        // given
        Item item = new Item();
        item.setItemName("Test Item");
        item.setPrice(1);
        item.setItemDetail("Test Description");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        // when
        Item savedItem = itemRepository.save(item);
        // then
        Assertions.assertEquals(savedItem.getId(), item.getId());
    }

    @Test
    @DisplayName("Testing : find Item from Repository using Item Name")
    public void findByItemNameTest(){
        // given
        this.createItemTest();
        // when
        List<Item> byItemName = itemRepository.findByItemName("Test Item");
        // then
        for (Item item : byItemName) {
            System.out.println("item.toString() = " + item.toString());
        }
    }

    @Test
    @DisplayName("Find Items only with price less than a number")
    public void findCheaperItemTest(){
        // given
        this.createItemList(); // Creates 6 items with price 10000 ~ 10005
        int limitPrice = 10003;

        // when
        List<Item> items = itemRepository.findByPriceLessThan(limitPrice); // find items cheaper than limit

        // then
        for (Item item : items) {
            Assertions.assertTrue(item.getPrice() < limitPrice);
        }
    }

    /* This function creates Item with 6 different prices (10000 ~ 10005) */
    public void createItemList(){
        for(int i = 10000; i <= 10005; i++){
            Item item = new Item();
            item.setItemName("Test Item");
            item.setPrice(i);
            item.setItemDetail("Test Description");
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

}