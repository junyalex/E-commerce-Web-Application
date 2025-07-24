package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import com.example.shop.repository.OrderItemRepository;
import com.example.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    @DisplayName("Cascade Testing")
    public void cascadeTest(){
        Order order = new Order();

        for(int i=0; i<3;i++){

            Item item = createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        // Saving order entity will save orderItems in DB too due to cascading.
        orderRepository.saveAndFlush(order);
        em.clear(); // save it to DB

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Test
    @DisplayName("Lazy loading test")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().getFirst().getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class = " + orderItem.getOrder().getClass());
        orderItem.getOrder().getOrderDate();
    }

    /**
     * Tests if removing orderItem from Order will cause orderItem being deleted from DB.
     */
    @Test
    @DisplayName("Orphan removal test")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().removeFirst();
        em.flush();
    }

    /**
     * Creates an Order object for testing and return it
     */
    public Order createOrder(){

        Order order = new Order();

        for(int i=0;i<3;i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    /**
     * Creates an Item object for testing and return it
     */
    public Item createItem(){
        Item item = new Item();
        item.setItemName("Test");
        item.setPrice(10000);
        item.setItemDetail("Description");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(10);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

}