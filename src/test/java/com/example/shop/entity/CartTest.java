package com.example.shop.entity;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Mapping test for Member & Cart")
    public void findCartAndMemberTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(cart.getMember().getId(), member.getId());

    }



    public Member createMember() { // 3
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("Hojun Lee");
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setPassword("1234");
        memberFormDto.setAddress("Toronto, Ontario");
        return Member.createMember(memberFormDto, passwordEncoder);

    }
}