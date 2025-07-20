package com.example.shop.service;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.entity.Member;
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
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Sign up test")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
    }

    @Test
    @DisplayName("Duplicate sign up test")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        Throwable e = assertThrows(IllegalStateException.class, ()->memberService.saveMember(member2));

        assertEquals("Account with this email already exists", e.getMessage());
    }

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("Hojun Lee");
        memberFormDto.setEmail("hoju.lee@mail.utoronto.ca");
        memberFormDto.setPassword("1234");
        memberFormDto.setAddress("Toronto");

        return Member.createMember(memberFormDto, passwordEncoder);
    }



}